package com.creamydark.avz.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.creamydark.avz.domain.some_api.JoYuriAuthenticationAPI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    joYuriAuthenticationAPI: JoYuriAuthenticationAPI
):ViewModel() {

    private val userData = joYuriAuthenticationAPI.userData
    val _userData = userData.asStateFlow()

    private val _currentUserData = joYuriAuthenticationAPI.currentUserData
    val currentUserData = _currentUserData.asStateFlow()

}