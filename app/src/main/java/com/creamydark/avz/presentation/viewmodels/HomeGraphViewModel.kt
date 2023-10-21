package com.creamydark.avz.presentation.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.creamydark.avz.domain.ResultType
import com.creamydark.avz.domain.model.UserData
import com.creamydark.avz.domain.usecase.CheckIfUserDataExistUseCases
import com.creamydark.avz.domain.usecase.FirebaseAuthListenerUseCase
import com.creamydark.avz.domain.usecase.GetUserExtraDataUsecase
import com.creamydark.avz.domain.usecase.SignOutUseCase
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
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
            authListenerUseCase.invoke().collectLatest {
                result->
                when(result){
                    is ResultType.Error -> {

                    }
                    ResultType.Loading -> {

                    }
                    is ResultType.Success -> {
                        getUserExtraDataUsecase.execute(uid = result.data).collect{
                                resultData->
                            when(resultData){
                                is ResultType.Error -> {

                                }
                                ResultType.Loading -> {

                                }
                                is ResultType.Success -> {
                                    userData.value = resultData.data
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    fun signOut(){
        viewModelScope.launch {
            signOutUseCase.signOut()
        }
    }
}