package com.creamydark.avz.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.creamydark.avz.TextToSpeechManager
import com.creamydark.avz.domain.ResultType
import com.creamydark.avz.domain.model.WordsDataModel
import com.creamydark.avz.domain.usecase.WordsFirestoreUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WordScrollViewModel @Inject constructor(
    private val wordsFirestoreUseCase: WordsFirestoreUseCase,
    private val textToSpeechManager: TextToSpeechManager
):ViewModel() {



    private val wordsList = MutableStateFlow<List<WordsDataModel>>(emptyList())
    val _wordsList = wordsList.asStateFlow()

    private val uploadResult = MutableStateFlow<ResultType<String>>(ResultType.loading())
    val _uploadResult = uploadResult.asStateFlow()

    private val word_tf = MutableStateFlow("")
    private val desc_tf = MutableStateFlow("")
    private val example_tf = MutableStateFlow("")

    val _word_tf =word_tf.asStateFlow()
    val _desc_tf =desc_tf.asStateFlow()
    val _example_tf =example_tf.asStateFlow()


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

    fun editWordTF(text:String) {
        word_tf.value = text
    }
    fun editDescTF(text:String) {
        desc_tf.value = text
    }
    fun editExampleTF(text:String) {
        example_tf.value = text
    }
    fun uploadWordsToFirestore(){
        val data = WordsDataModel(
            title = word_tf.value,
            description = desc_tf.value,
            example = example_tf.value
        )
        viewModelScope.launch {
            wordsFirestoreUseCase.upload(data).collect{
                result->
                uploadResult.value = result
            }
        }
    }


    override fun onCleared() {
        super.onCleared()
        textToSpeechManager.shutdown()
    }
}