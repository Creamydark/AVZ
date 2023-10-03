package com.creamydark.avz.viewmodels

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.creamydark.avz.datamodels.FirebaseAccountResponseData
import com.creamydark.avz.repository.UserFirebaseAccountRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val repo:UserFirebaseAccountRepository):ViewModel() {

    private val registerResult = MutableStateFlow(FirebaseAccountResponseData())
    val _registerResult = registerResult.asStateFlow()

    fun signUp(
        email:String,
        password:String,
        name:String,
        userType:String
    ){
        viewModelScope.launch(Dispatchers.IO){
            repo.signUpAccount(email, password, name, userType).collect{
                registerResult.value = it
            }
        }
    }
}