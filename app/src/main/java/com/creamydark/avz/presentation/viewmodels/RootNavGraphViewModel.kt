package com.creamydark.avz.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.creamydark.avz.domain.ResultType
import com.creamydark.avz.domain.usecase.CheckIfUserDataExistUseCases
import com.creamydark.avz.domain.usecase.FirebaseAuthListenerUseCase
import com.creamydark.avz.domain.usecase.GetUserExtraDataUsecase
import com.creamydark.avz.domain.usecase.SignInUserUsingCredentialsUseCases
import com.creamydark.avz.domain.usecase.SignOutUseCase
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RootNavGraphViewModel @Inject constructor(
    private val firebaseAuthListenerUseCase: FirebaseAuthListenerUseCase,
    private val googleSignInClient: GoogleSignInClient,
    private val signInUserUsingCredentialsUseCases: SignInUserUsingCredentialsUseCases,
    private val signOutUseCase: SignOutUseCase,
    private val checkIfUserDataExistUseCases: CheckIfUserDataExistUseCases
):ViewModel() {



    private val isAuthenticated = MutableStateFlow(false)
    val _isAuthenticated = isAuthenticated.asStateFlow()

    private val errorSignInWithCreds = MutableStateFlow("")
    val _errorSignInWithCreds = errorSignInWithCreds.asStateFlow()

    private val userdataExistState = MutableStateFlow(true)
    val _userdataExistState = userdataExistState.asStateFlow()
    init {
        viewModelScope.launch(Dispatchers.IO) {
            firebaseAuthListenerUseCase.invoke().collectLatest {
                result ->
                when(result){
                    is ResultType.Error -> {

                        isAuthenticated.value = false
                    }
                    ResultType.Loading -> {
                        isAuthenticated.value = false

                    }
                    is ResultType.Success -> {
                        isAuthenticated.value = true
                        checkIfUserDataExistUseCases.check(result.data).collect{
                            resultData ->
                            when(resultData){
                                is ResultType.Error -> {
                                    userdataExistState.value = false
                                }
                                ResultType.Loading -> {

                                }
                                is ResultType.Success -> {
                                    userdataExistState.value = resultData.data
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    fun googleSignInAccount() = googleSignInClient

    fun signInWithGoogle(account:GoogleSignInAccount){
        viewModelScope.launch {
            signInUserUsingCredentialsUseCases.signIn(account = account).collect{
                result ->
                result.onSuccess {
                    errorSignInWithCreds.value = it
                }
                result.onFailure {
                    errorSignInWithCreds.value = it.message.toString()
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