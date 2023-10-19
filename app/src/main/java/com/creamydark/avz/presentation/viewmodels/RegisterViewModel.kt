package com.creamydark.avz.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.creamydark.avz.domain.ResultType
import com.creamydark.avz.domain.model.UserData
import com.creamydark.avz.domain.usecase.AddUserExtraDataUseCases
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val addUserExtraDataUseCases: AddUserExtraDataUseCases,
    private val googleSignInAccount: GoogleSignInAccount?
):ViewModel() {


    private val localDate = MutableStateFlow(LocalDate.now())
    val _localDate = localDate.asStateFlow()

    private val userType = MutableStateFlow(false)
    val _userType = userType.asStateFlow()

    private val registerResult =MutableStateFlow(false)
    val _registerResult = registerResult.asStateFlow()

    private val errorText = MutableStateFlow("")
    val _errorText = errorText.asStateFlow()


    fun editLocalDateForBday(date: LocalDate){
        localDate.value = date
    }
    fun editUserType(type:Boolean){
        userType.value = type
    }
    fun registerUserExtraData(){
        viewModelScope.launch {
            addUserExtraDataUseCases.execute(localDate.value , userType = userType.value).collect{
                    result ->
                when(result){
                    is ResultType.Error -> {
                        registerResult.value = false
                        errorText.value = result.message
                    }
                    ResultType.Loading -> {

                    }
                    is ResultType.Success -> {
                        registerResult.value = true
                    }
                }
            }
        }
    }

}