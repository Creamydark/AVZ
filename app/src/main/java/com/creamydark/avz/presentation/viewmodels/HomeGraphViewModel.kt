package com.creamydark.avz.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.creamydark.avz.domain.usecase.SignOutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeGraphViewModel @Inject constructor(
    private val signOutUseCase: SignOutUseCase,
):ViewModel() {


    fun signOut(){
        viewModelScope.launch {
            signOutUseCase.signOut()
        }
    }
}