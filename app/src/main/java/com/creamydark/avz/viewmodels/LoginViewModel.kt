package com.creamydark.avz.viewmodels

import android.text.TextUtils
import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.creamydark.avz.datamodels.FirebaseAccountResponseData
import com.creamydark.avz.repository.UserFirebaseAccountRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val repo :UserFirebaseAccountRepository):ViewModel() {

    private val signInResult = MutableStateFlow(FirebaseAccountResponseData())
    val _signInResult = signInResult.asStateFlow()

    private val email = MutableStateFlow("")
    val _email = email.asStateFlow()

    private val password = MutableStateFlow("")
    val _password = password.asStateFlow()

    private val showPassword = MutableStateFlow(false)
    val _showPassword = showPassword.asStateFlow()

    private val emailErrorState = MutableStateFlow(false)

    val _emailErrorState = emailErrorState.asStateFlow()

    private val loginBtnState = MutableStateFlow(true)
    val _loginBtnState = loginBtnState.asStateFlow()

    private val alertDialogState = MutableStateFlow(false)
    val _alertDialogState = alertDialogState.asStateFlow()




    fun alertDialogState(value :Boolean){
        alertDialogState.value = value
        if (!value){
            signInResult.value = FirebaseAccountResponseData()
        }
    }

    fun editEmail(text: String){
        email.value = text
        emailErrorState.value = !checkEmail(text)
    }

    fun editPassword(text:String){
        password.value = text
    }
    fun showBtnPassword(value:Boolean){
        showPassword.value = value
    }

    fun signIn(){
        viewModelScope.launch(context = Dispatchers.IO){
            repo.signInAccount(email.value, password.value).flowOn(Dispatchers.IO).collect{
                    result ->
                loginBtnState.value = !result.isLoading
                signInResult.value = result
            }
        }
    }

    fun signOut(){
        viewModelScope.launch {
            repo.signOut()
        }
    }
}

fun checkEmail(email:String):Boolean=!TextUtils.isEmpty(email)&&Patterns.EMAIL_ADDRESS.matcher(email).matches()
