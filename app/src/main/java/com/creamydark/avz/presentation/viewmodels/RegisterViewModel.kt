package com.creamydark.avz.presentation.viewmodels

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor():ViewModel() {
    private val name = MutableStateFlow("")
    var age = MutableStateFlow(0)
    var birthday = MutableStateFlow(0)
    var username = MutableStateFlow("")
    var isStudent = MutableStateFlow(false)
}