package com.creamydark.avz.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.creamydark.avz.domain.model.UserData
import com.creamydark.avz.domain.usecase.CheckIfUserSignedInListenerUseCase
import com.creamydark.avz.domain.usecase.GetUserExtraDataUsecase
import com.creamydark.avz.domain.usecase.SignInUserUsingCredentialsUseCases
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RootNavGraphViewModel @Inject constructor(
    private val checkIfUserSignedInListenerUseCase: CheckIfUserSignedInListenerUseCase,
    private val getUserExtraDataUsecase: GetUserExtraDataUsecase,
    private val userCurrent:GoogleSignInAccount?,
    private val googleSignInClient: GoogleSignInClient,
    private val signInUserUsingCredentialsUseCases: SignInUserUsingCredentialsUseCases
):ViewModel() {
    private val googleClientUser = MutableStateFlow(false)
    val _googleClientUser = googleClientUser.asStateFlow()

    private val userExtraDataExist = MutableStateFlow(false)
    val _userExtraDataExist = userExtraDataExist.asStateFlow()


    private val userExtraData = MutableStateFlow<UserData?>(UserData())
    val _userExtraData = userExtraData.asStateFlow()

    init {
        viewModelScope.launch {
            checkIfUserSignedInListenerUseCase.invoke().collect{
                googleClientUser.value = it
            }
        }
        viewModelScope.launch {
            userCurrent?.id?.let { uid->
                getUserExtraDataUsecase.execute(uid).collect {
                    result->
                    result.onSuccess {
                        data ->
                        userExtraDataExist.value = true
                        userExtraData.value = data
                    }
                    result.onFailure {
                        userExtraDataExist.value = false
                    }
                }
            }
        }
    }

    fun googleSignInAccount() = googleSignInClient

    fun signInWithGoogle(account:GoogleSignInAccount){
        viewModelScope.launch {
            signInUserUsingCredentialsUseCases.signIn(account = account).collect{

            }
        }
    }


}