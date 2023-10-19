package com.creamydark.avz.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
    private val wordsFirestoreUseCase: WordsFirestoreUseCase
):ViewModel() {


    private val wordsList = MutableStateFlow<List<WordsDataModel>>(emptyList())
    val _wordsList = wordsList.asStateFlow()

    private val uploadResult = MutableStateFlow<ResultType<String>>(ResultType.loading())
    val _uploadResult = uploadResult.asStateFlow()

    init {
        viewModelScope.launch {
            wordsFirestoreUseCase.getAllWords().collect{
                result ->
                when(result){
                    is ResultType.Error -> {

                    }
                    ResultType.Loading -> {

                    }
                    is ResultType.Success -> {
                        wordsList.value = result.data
                    }
                }
            }
        }
    }
    fun addWords(data : WordsDataModel){
        viewModelScope.launch {
            wordsFirestoreUseCase.upload(data).collect{
                result->
                uploadResult.value = result
            }
        }
    }



}