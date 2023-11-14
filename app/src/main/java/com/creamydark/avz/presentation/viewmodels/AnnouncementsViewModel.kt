package com.creamydark.avz.presentation.viewmodels

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import com.creamydark.avz.domain.ResultType
import com.creamydark.avz.domain.model.AnnouncementPostData
import com.creamydark.avz.domain.some_api.JoYuriAuthenticationAPI
import com.creamydark.avz.domain.usecase.DeleteUpdatePostFirestore
import com.creamydark.avz.domain.usecase.GetAllAnnouncementsUseCase
import com.creamydark.avz.domain.usecase.GetPostImageUseCase
import com.creamydark.avz.domain.usecase.UploadAnnouncementPostUseCase
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnnouncementsViewModel @Inject constructor(
    joYuriAuthenticationAPI: JoYuriAuthenticationAPI,
    private val uploadAnnouncementPostUseCase: UploadAnnouncementPostUseCase,
    private val getAllAnnouncementsUseCase: GetAllAnnouncementsUseCase,
    private val getPostImageUseCase: GetPostImageUseCase,
    private val firebaseStorage: FirebaseStorage,
    private val deleteUpdatePostFirestore: DeleteUpdatePostFirestore
):ViewModel() {

    val userData = joYuriAuthenticationAPI.userData
    val emailUploader  = joYuriAuthenticationAPI.getEmail()?:""
    val displayName  = joYuriAuthenticationAPI.getName()?:""
    val profilePhoto = joYuriAuthenticationAPI.getPhotUri()
    private val _postList =MutableStateFlow<List<AnnouncementPostData>>(emptyList())
    val postList = _postList.asStateFlow()

    private val _deleteResult = MutableStateFlow<ResultType<String>>(ResultType.loading())
    val deleteResult = _deleteResult.asStateFlow()
    private val _resultUpload= MutableStateFlow<ResultType<String>>(ResultType.loading())
    val resultUpload = _resultUpload.asStateFlow()
    init {
        viewModelScope.launch(
            Dispatchers.IO
        ) {
            getAllAnnouncementsUseCase.invoke().collectLatest {
                result->
                when(result){
                    is ResultType.Error -> {

                    }
                    ResultType.Loading -> {

                    }
                    is ResultType.Success -> {
                        _postList.value = result.data.sortedBy {
                            it.timestamp
                        }
                    }
                }
            }
        }
    }
    fun post(caption:String,image:Uri?){
        viewModelScope.launch(Dispatchers.IO){
            val timestamp = System.currentTimeMillis()
            uploadAnnouncementPostUseCase.invoke(displayName, emailUploader, caption, timestamp, image, profilePhoto).collectLatest {
                result->
                _resultUpload.value = result
            }
        }
    }

    fun deletePost(postData: AnnouncementPostData){
        viewModelScope.launch(Dispatchers.IO) {
            deleteUpdatePostFirestore.invokeDelete(postData).apply {
                _deleteResult.update {
                    this
                }
            }
        }
    }
    fun getPostImageUseCase() = getPostImageUseCase
    fun firebaseStorage() = firebaseStorage
}