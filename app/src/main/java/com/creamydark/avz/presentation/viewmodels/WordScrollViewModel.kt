package com.creamydark.avz.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.creamydark.avz.TextToSpeechManager
import com.creamydark.avz.domain.ResultType
import com.creamydark.avz.domain.model.WordsDataModel
import com.creamydark.avz.domain.some_api.JoYuriAuthenticationAPI
import com.creamydark.avz.domain.usecase.AddWordsFirestoreUseCase
import com.creamydark.avz.domain.usecase.GenerateRandomWordsUseCase
import com.creamydark.avz.domain.usecase.GetAllWordsFromFirestoreUseCase
import com.creamydark.avz.domain.usecase.UpdateFavoriteWordsUseCase
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
    private val textToSpeechManager: TextToSpeechManager,
    private val updateFavoriteWordsUseCase: UpdateFavoriteWordsUseCase,
    private val generateRandomWordsUseCase: GenerateRandomWordsUseCase,
    private val getAllWordsFromFirestoreUseCase: GetAllWordsFromFirestoreUseCase,
    joYuriAuthenticationAPI: JoYuriAuthenticationAPI
):ViewModel() {


    private val wordsList = MutableStateFlow(listOf<WordsDataModel>())
    val _wordsList = wordsList.asStateFlow()

    private val searchResultList_ = MutableStateFlow(listOf<WordsDataModel>())
    val searchResultList = searchResultList_.asStateFlow()

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


    private val _uploadResult = MutableStateFlow<ResultType<String>>(ResultType.loading())
    private val _addFavoriteResult = MutableStateFlow<ResultType<String>>(ResultType.loading())

    val uploadResult = _uploadResult.asStateFlow()
    val addFavoriteResult = _addFavoriteResult.asStateFlow()




    init {
        viewModelScope.launch(Dispatchers.IO) {
            getAllWordsFromFirestoreUseCase.invoke().collectLatest {
                result->
                wordsList.update {
                    result.sortedBy {
                        it.id
                    }
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
        val data = if (word.isNotBlank()&&description.isNotBlank()&&example.isNotBlank()){
            val timestamp = System.currentTimeMillis()
            WordsDataModel(
                title = word,
                description = description,
                example = example,
                uploader = email?:"none",
                id = timestamp
            )
        }else{
            null
        }
        viewModelScope.launch {
            addWordsFirestoreUseCase.upload(data).collect{
                result->
                _uploadResult.value = result
            }
        }
    }

    fun addFavorites(
        title:String
    ){
        email?.let {
            emailll->
            viewModelScope.launch(Dispatchers.IO) {
                updateFavoriteWordsUseCase(emailll, title).collectLatest {
                    result->
                    _addFavoriteResult.value = result
                }
            }
        }
    }


    override fun onCleared() {
        super.onCleared()
        textToSpeechManager.shutdown()
    }
}