package com.creamydark.avz.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.creamydark.avz.domain.some_api.JoYuriAuthenticationAPI
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    joYuriAuthenticationAPI: JoYuriAuthenticationAPI
):ViewModel() {

    val currentFirebaseUser = joYuriAuthenticationAPI.getCurrentFirebaseUser()
    val userData = joYuriAuthenticationAPI.userData

}