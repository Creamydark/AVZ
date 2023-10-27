package com.creamydark.avz.presentation.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.creamydark.avz.domain.model.UserData
import com.creamydark.avz.domain.usecase.FirebaseAuthListenerUseCase
import com.creamydark.avz.domain.usecase.GetUserExtraDataUsecase
import com.creamydark.avz.domain.usecase.SignOutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeGraphViewModel @Inject constructor(
    private val signOutUseCase: SignOutUseCase,
    private val getUserExtraDataUsecase: GetUserExtraDataUsecase,
    private val authListenerUseCase: FirebaseAuthListenerUseCase,
    context: Context
):ViewModel() {

    private val userData = MutableStateFlow(UserData())
    val _userData = userData.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {

        }
    }
    fun signOut(){
        viewModelScope.launch {
            signOutUseCase.signOut()
        }
    }
}