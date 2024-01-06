package com.creamydark.avz.presentation.viewmodels

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.creamydark.avz.data.repository.LessonsFirebaseRepository
import com.creamydark.avz.domain.model.LessonData
import com.creamydark.avz.domain.model.ResultType
import com.creamydark.avz.domain.some_api.JoYuriAuthenticationAPI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LessonsViewModel @Inject constructor(
    private val repository: LessonsFirebaseRepository,
    joYuriAuthenticationAPI: JoYuriAuthenticationAPI
) : ViewModel() {

    val userData = joYuriAuthenticationAPI.userData
    private val _lessonList = MutableStateFlow<List<LessonData>>(emptyList())
    val lessonList = _lessonList.asStateFlow()

    private val _createLessonResult = MutableStateFlow<ResultType<String>>(ResultType.loading())
    val createLessonResult = _createLessonResult.asSharedFlow()


    private val  _pdfResultFromNet= MutableStateFlow<Uri?>(null)
    val pdfResultFromNet = _pdfResultFromNet.asStateFlow()

    private val _selectedLesson = MutableStateFlow<LessonData?>(null)
    val selectedLesson = _selectedLesson.asStateFlow()

    private val _removeLessonResult = MutableStateFlow<ResultType<String>>(ResultType.loading())
    val removeLessonResult = _removeLessonResult.asStateFlow()
    private val _updateLessonResult = MutableStateFlow<ResultType<String>>(ResultType.loading())
    val updateLessonResult = _updateLessonResult.asStateFlow()
    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getLessonsList().collectLatest {
                yungList ->
                _lessonList.update {
                    yungList
                }
            }
        }
    }

    fun downloadPdfFromInternet(url :String,id:String){
        viewModelScope.launch(Dispatchers.IO) {
            repository.downloadPdfFromInternet(url = url, id = id).collectLatest {
                value: ResultType<Uri?> ->
                when(value){
                    is ResultType.Error -> {

                    }
                    ResultType.Loading ->{

                    }
                    is ResultType.Success -> {
                        val data = value.data
                        _pdfResultFromNet.value = data
                    }
                }
            }
        }
    }
    fun createLesson(data: LessonData,uri: Uri?){
        viewModelScope.launch(Dispatchers.IO) {
            repository.createLesson(data,uri).collectLatest {
                value: ResultType<String> ->
                _createLessonResult.value = value
            }
        }
    }

    fun updateLesson(data: LessonData?){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateLesson(data).collectLatest {
                Log.d("LessonsViewModel", "updateLesson: $it")
                _updateLessonResult.value = it
                when(it){
                    is ResultType.Success->{
                        data?.let {
                            sheesh->
                            _selectedLesson.value = sheesh
                        }
                    }
                    else->{

                    }
                }
            }
        }
    }
    fun removeLessonFromDatabase(data: LessonData){
        viewModelScope.launch(Dispatchers.IO) {
            repository.removeLesson(data).collectLatest {
                result->
                _removeLessonResult.value = result
                when(result){
                    is ResultType.Error -> {
                        val error = result.exception
                        Log.d("LessonsViewModel", "removeLessonFromDatabase:${data.id} | $error")
                    }
                    ResultType.Loading -> {

                    }
                    is ResultType.Success -> {
                        Log.d("LessonsViewModel", "removeLessonFromDatabase: ${result.data}")
                    }
                }
            }
        }
    }
    fun removeResultReset(){
        _removeLessonResult.value = ResultType.loading()
    }

    fun selectLesson(data:LessonData?){
        _selectedLesson.update { data }
    }
    fun onClearedModel(){
        _createLessonResult.value = ResultType.loading()
        _pdfResultFromNet.value = null
    }
}