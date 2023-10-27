package com.creamydark.avz.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.creamydark.avz.domain.some_api.JoYuriAuthenticationAPI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val joYuriAuthenticationAPI: JoYuriAuthenticationAPI
):ViewModel() {

    private val userData = joYuriAuthenticationAPI.userData
    val _userData = userData.asStateFlow()

    private val name = MutableStateFlow(joYuriAuthenticationAPI.getName())
    private val email = MutableStateFlow(joYuriAuthenticationAPI.getEmail())
    private val photoUri = MutableStateFlow(joYuriAuthenticationAPI.getPhotUri())

    val _name = name.asStateFlow()
    val _email = email.asStateFlow()
    val _photoUri = photoUri.asStateFlow()

}