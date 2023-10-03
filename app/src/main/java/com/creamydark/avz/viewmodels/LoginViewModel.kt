package com.creamydark.avz.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.creamydark.avz.datamodels.AlertDialogData
import com.creamydark.avz.datamodels.FirebaseAccountResponseData
import com.creamydark.avz.datamodels.ResultResponse
import com.creamydark.avz.repository.UserFirebaseAccountRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val repo :UserFirebaseAccountRepository):ViewModel() {

    private val signInResult = MutableStateFlow(FirebaseAccountResponseData())
    val _signInResult = signInResult.asStateFlow()

    fun dialogState(state : Boolean){
        signInResult.update {
            FirebaseAccountResponseData(isLoading = state)
        }
    }


    fun signIn(email: String,password: String){
        viewModelScope.launch(context = Dispatchers.IO){
            repo.signInAccount(email, password).flowOn(Dispatchers.IO).collect{
                    result ->
                when(result){
                    is ResultResponse.Loading ->{
                        signInResult.value = FirebaseAccountResponseData(isLoading = true)
                    }
                    is ResultResponse.Success -> {
                        signInResult.value = FirebaseAccountResponseData(isSuccessful = "Sign in sucessfully", isLoading = true)
                    }
                    is ResultResponse.Error -> {
                        result.message?.let {
                            errorMe->
                            signInResult.value = FirebaseAccountResponseData(error = errorMe, isLoading = true)
                        }
                    }
                }
            }
        }
    }

    fun signOut(){
        viewModelScope.launch {
            repo.signOut()
        }
    }
}