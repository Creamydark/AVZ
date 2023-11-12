package com.creamydark.avz.presentation.viewmodels



import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.creamydark.avz.domain.ResultType
import com.creamydark.avz.domain.model.UserData
import com.creamydark.avz.domain.some_api.JoYuriAuthenticationAPI
import com.creamydark.avz.domain.usecase.AddUserExtraDataUseCases
import com.creamydark.avz.domain.usecase.FirebaseAuthListenerUseCase
import com.creamydark.avz.domain.usecase.SignInUserUsingCredentialsUseCases
import com.creamydark.avz.inozienum.UserAuthenticationState
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class RootNavGraphViewModel @Inject constructor(
    private val firebaseAuthListenerUseCase: FirebaseAuthListenerUseCase,
    private val googleSignInClient: GoogleSignInClient,
    private val signInUserUsingCredentialsUseCases: SignInUserUsingCredentialsUseCases,
    private val addUserExtraDataUseCases: AddUserExtraDataUseCases,
    private val joYuriAuthenticationAPI: JoYuriAuthenticationAPI
):ViewModel() {

    private val errorSignInWithCreds = MutableStateFlow("")
    val _errorSignInWithCreds = errorSignInWithCreds.asStateFlow()
    fun getAuthState() = joYuriAuthenticationAPI.getUserAuthenticationState()

    init {

    }
    fun initialize(){
        viewModelScope.launch(Dispatchers.IO) {
            joYuriAuthenticationAPI.updateUserAuthenticationState(UserAuthenticationState.Unauthenticated)
            firebaseAuthListenerUseCase.invoke()
        }
    }
    fun uploadDataToFirestore(userType: Boolean){
        viewModelScope.launch(Dispatchers.IO) {
            val email = joYuriAuthenticationAPI.getEmail()
            val uid = joYuriAuthenticationAPI.getUid()
            val name = joYuriAuthenticationAPI.getName()
            val data = UserData(
                student = userType,
                uid = uid,
                email = email,
                name = name
            )
            addUserExtraDataUseCases.execute(data = data).collect(){
                result ->
                when(result){
                    is ResultType.Error -> {
                        Log.d("RootNavGraphViewModel", "uploadDataToFirestore: Error")
                    }
                    ResultType.Loading -> {

                    }
                    is ResultType.Success -> {
                        Log.d("RootNavGraphViewModel", "uploadDataToFirestore: success")
                    }
                }
            }
        }
    }
    fun googleSignInAccount() = googleSignInClient
    fun signInWithGoogle(account:GoogleSignInAccount){
        viewModelScope.launch(Dispatchers.IO) {
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

}