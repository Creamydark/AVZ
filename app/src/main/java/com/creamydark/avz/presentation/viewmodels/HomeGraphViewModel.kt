package com.creamydark.avz.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.creamydark.avz.domain.ResultType
import com.creamydark.avz.domain.model.UserData
import com.creamydark.avz.domain.usecase.CheckIfUserDataExistUseCases
import com.creamydark.avz.domain.usecase.FirebaseAuthListenerUseCase
import com.creamydark.avz.domain.usecase.GetUserExtraDataUsecase
import com.creamydark.avz.domain.usecase.SignOutUseCase
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeGraphViewModel @Inject constructor(
    private val getUserExtraDataUsecase: GetUserExtraDataUsecase,
    private val signOutUseCase: SignOutUseCase,
    private val checkIfUserDataExistUseCases: CheckIfUserDataExistUseCases,
    private val auth: FirebaseAuth
):ViewModel() {

    private val userExtraData = MutableStateFlow<UserData?>(null)
    val _userExtraData = userExtraData.asStateFlow()

    init {
        viewModelScope.launch {
            getUserExtraDataUsecase.execute().collectLatest {
                result->
                result.onSuccess {
                    userExtraData.value = it
                }
            }
        }
    }
    fun signOut(){
        viewModelScope.launch {
            signOutUseCase.signOut()
        }
    }
}