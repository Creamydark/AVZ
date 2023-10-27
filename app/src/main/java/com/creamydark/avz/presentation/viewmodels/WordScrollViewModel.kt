package com.creamydark.avz.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.creamydark.avz.TextToSpeechManager
import com.creamydark.avz.domain.ResultType
import com.creamydark.avz.domain.model.WordsDataModel
import com.creamydark.avz.domain.some_api.JoYuriAuthenticationAPI
import com.creamydark.avz.domain.usecase.UpdateFavoriteWordsUseCase
import com.creamydark.avz.domain.usecase.WordsFirestoreUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WordScrollViewModel @Inject constructor(
    private val wordsFirestoreUseCase: WordsFirestoreUseCase,
    private val textToSpeechManager: TextToSpeechManager,
    private val updateFavoriteWordsUseCase: UpdateFavoriteWordsUseCase,
    joYuriAuthenticationAPI: JoYuriAuthenticationAPI
):ViewModel() {

    private val wordsList = MutableStateFlow<List<WordsDataModel>>(emptyList())
    val _wordsList = wordsList.asStateFlow()

    val email = joYuriAuthenticationAPI.getEmail()

    val userData = joYuriAuthenticationAPI.userData

    private val _uploadResult = MutableStateFlow<ResultType<String>>(ResultType.loading())
    private val _addFavoriteResult = MutableStateFlow<ResultType<String>>(ResultType.loading())

    val uploadResult = _uploadResult.asStateFlow()
    val addFavoriteResult = _addFavoriteResult.asStateFlow()

    init {
        viewModelScope.launch {
            wordsFirestoreUseCase.getAllWords().collect{
                result ->
                wordsList.value = result
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
            WordsDataModel(
                title = word,
                description = description,
                example = example,
                uploader = email?:"none"
            )
        }else{
            null
        }
        viewModelScope.launch {
            wordsFirestoreUseCase.upload(data).collect{
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