package com.creamydark.avz.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.creamydark.avz.repository.UserFirebaseAccountRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RootNavGraphViewModel @Inject constructor(private val repo :UserFirebaseAccountRepository):ViewModel() {
    private val userState = MutableStateFlow(false)
    val _userState = userState.asStateFlow()

    init {
        viewModelScope.launch {
            repo.checkIfUserAlreadySignedIn().collect{
                userState.value = it
            }
        }
    }

}