package com.creamydark.avz.presentation.viewmodels

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.creamydark.avz.domain.model.ResultType
import com.creamydark.avz.domain.model.UpdatesPostData
import com.creamydark.avz.domain.some_api.JoYuriAuthenticationAPI
import com.creamydark.avz.domain.usecase.postupdates.DeletePostUpdatesFirestoreUseCase
import com.creamydark.avz.domain.usecase.postupdates.EditPostUpdateFirestoreUseCase
import com.creamydark.avz.domain.usecase.postupdates.GetAllPostUpdatesUseCase
import com.creamydark.avz.domain.usecase.postupdates.PostNewUpdateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnnouncementsViewModel @Inject constructor(
    joYuriAuthenticationAPI: JoYuriAuthenticationAPI,
    private val postNewUpdateUseCase: PostNewUpdateUseCase,
    private val getAllPostUpdatesUseCase: GetAllPostUpdatesUseCase,
    private val deletePostUpdatesFirestoreUseCase: DeletePostUpdatesFirestoreUseCase,
    private val editPostUpdateFirestoreUseCase: EditPostUpdateFirestoreUseCase
):ViewModel() {

    val userData = joYuriAuthenticationAPI.userData
    val emailUploader  = joYuriAuthenticationAPI.getEmail()?:""
    val displayName  = joYuriAuthenticationAPI.getName()?:""
    val profilePhoto = joYuriAuthenticationAPI.getPhotUri()
    private val _postList =MutableStateFlow<List<UpdatesPostData>>(emptyList())
    val postList = _postList.asStateFlow()

    private val _deleteResult = MutableStateFlow<ResultType<String>>(ResultType.loading())
    val deleteResult = _deleteResult.asStateFlow()
    private val _resultUpload= MutableStateFlow<ResultType<String>>(ResultType.loading())
    val resultUpload = _resultUpload.asStateFlow()
    private val _editPostResult = MutableStateFlow<ResultType<String>>(ResultType.loading())
    val editPostResult = _editPostResult.asSharedFlow()


    init {
        viewModelScope.launch(
            Dispatchers.IO
        ) {
            getAllPostUpdatesUseCase.invoke().collectLatest {
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

    fun editPost(data: UpdatesPostData){
        viewModelScope.launch(Dispatchers.IO) {
            editPostUpdateFirestoreUseCase.invoke(data = data).collectLatest {
                awit->
                _editPostResult.update {
                    awit
                }
            }
        }
    }
    fun post(caption:String,image:Uri?){
        viewModelScope.launch(Dispatchers.IO){
            val data = UpdatesPostData(
                emailUploader,
                displayName,
                caption,
                profilePhoto = profilePhoto.toString()
            )
            postNewUpdateUseCase.invoke(data = data, postImg = image).collectLatest {
                result->
                _resultUpload.value = result
            }
        }
    }

    fun deletePost(postData: UpdatesPostData){
        viewModelScope.launch(Dispatchers.IO) {
            deletePostUpdatesFirestoreUseCase.invokeDelete(postData).apply {
                _deleteResult.update {
                    this
                }
            }
        }
    }
}