package com.creamydark.avz.presentation.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.creamydark.avz.TextToSpeechManager
import com.creamydark.avz.domain.model.ResultType
import com.creamydark.avz.domain.model.WordsDataModel
import com.creamydark.avz.domain.some_api.JoYuriAuthenticationAPI
import com.creamydark.avz.domain.usecase.wordsvocabulary.AddWordsFirestoreUseCase
import com.creamydark.avz.domain.usecase.wordsvocabulary.DeleteVocabularyWordUseCase
import com.creamydark.avz.domain.usecase.wordsvocabulary.GenerateRandomWordsUseCase
import com.creamydark.avz.domain.usecase.wordsvocabulary.GetAllWordsFromFirestoreUseCase
import com.creamydark.avz.domain.usecase.userclient.UpdateUserFavoriteWordsUseCase
import com.creamydark.avz.domain.usecase.wordsvocabulary.UpdateWordsToFirestoreUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WordScrollViewModel @Inject constructor(
    private val addWordsFirestoreUseCase: AddWordsFirestoreUseCase,
    private val updateUserFavoriteWordsUseCase: UpdateUserFavoriteWordsUseCase,
    private val generateRandomWordsUseCase: GenerateRandomWordsUseCase,
    private val getAllWordsFromFirestoreUseCase: GetAllWordsFromFirestoreUseCase,
    private val deleteVocabularyWordUseCase: DeleteVocabularyWordUseCase,
    private val updateWordsToFirestoreUseCase: UpdateWordsToFirestoreUseCase,
    private val context: Context,
    joYuriAuthenticationAPI: JoYuriAuthenticationAPI
):ViewModel() {


    private val textToSpeechManager = TextToSpeechManager(context)

    private val wordsList = MutableStateFlow(listOf<WordsDataModel>())
    val _wordsList = wordsList.asStateFlow()

    private val searchResultList_ = MutableStateFlow(listOf<WordsDataModel>())
    val searchResultList = searchResultList_.asStateFlow()

    private val _deleteWordResult = MutableStateFlow<ResultType<String>>(ResultType.loading())
    val deleteWordResult = _deleteWordResult.asStateFlow()

    private val _uploadResult = MutableStateFlow<ResultType<String>>(ResultType.loading())
    val uploadResult = _uploadResult.asStateFlow()

    private val _addFavoriteResult = MutableStateFlow<ResultType<String>>(ResultType.loading())
    val addFavoriteResult = _addFavoriteResult.asStateFlow()

    fun editSearch(text:String){
        viewModelScope.launch {
            val sl = arrayListOf<WordsDataModel>()
            wordsList.value.forEach {
                item ->
                if (item.title.contains(other = text, ignoreCase = true)){
                    sl.add(item)
                }
            }
            searchResultList_.update {
                sl
            }
        }
    }

    val userData = joYuriAuthenticationAPI.userData


    val email = joYuriAuthenticationAPI.getEmail()







    init {
        viewModelScope.launch(Dispatchers.IO) {
            getAllWordsFromFirestoreUseCase.invoke().collectLatest {
                result->
                val list = result?: emptyList()
                wordsList.update {
                    list
                }
            }
            /*addWordsFirestoreUseCase.getAllWords().collect{
                result ->
                wordsList.value = result
            }*/
        }
    }

    fun generateKaseLastPageNa(){
        viewModelScope.launch(Dispatchers.IO) {
            for (a in 1..10){
                generateRandomWordsUseCase.invoke().collectLatest {
                        result->
                    when(result){
                        is ResultType.Error -> {
                            val message = result.exception.message
                            Log.d("WordScrollViewModel", "generateRandomWordsUseCase:Error $message")
                        }
                        ResultType.Loading -> {

                        }
                        is ResultType.Success -> {
                            val data = result.data?:"Unknownnnnnn"
                            val list = ArrayList(wordsList.value)
                            list.add(WordsDataModel(title = data))
                            wordsList.value = list
                        }
                    }
                }
            }
        }
    }

    fun speak(text:String){
        textToSpeechManager.speak(text)
    }


    fun uploadWordsToFirestore(
        word:String,description:String,example:String
    ){
        val data = WordsDataModel(
            title = word,
            description = description,
            example = example,
            uploader = email?:"none"
        )
        viewModelScope.launch {
            addWordsFirestoreUseCase.upload(data).apply {
                _uploadResult.update {
                    this
                }
            }
        }
    }

    fun addFavorites(
        title:String
    ){
        email?.let {
            emailll->
            viewModelScope.launch(Dispatchers.IO) {
                updateUserFavoriteWordsUseCase(emailll, title).collectLatest {
                    result->
                    _addFavoriteResult.value = result
                }
            }
        }
    }


    fun deleteWord(dataModel: WordsDataModel){
        viewModelScope.launch(Dispatchers.IO) {
            deleteVocabularyWordUseCase.invokeDelete(dataModel).apply {
                _deleteWordResult.update {
                    this
                }
            }
        }
    }

    fun updateWord(dataModel: WordsDataModel){
        viewModelScope.launch(Dispatchers.IO) {
            updateWordsToFirestoreUseCase.invoke(dataModel).apply {

            }
        }
    }
    override fun onCleared() {
        super.onCleared()
        textToSpeechManager.shutdown()
    }
}