package com.creamydark.avz.domain.some_api

import com.creamydark.avz.domain.model.UserData
import com.creamydark.avz.enozienum.UserAuthenticationState
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject


class JoYuriAuthenticationAPI @Inject constructor(
) {
    private val userAuthenticationState = MutableStateFlow(UserAuthenticationState.Loading)
    private val currentFirebaseUser = MutableStateFlow<FirebaseUser?>(null)
    private val _userData = MutableStateFlow<UserData?>(null)
    val userData = _userData.asStateFlow()
    fun updateUserData(data:UserData?){
        _userData.value = data
    }
    fun getUserAuthenticationState():StateFlow<UserAuthenticationState> = userAuthenticationState
    fun updateUserAuthenticationState(value:UserAuthenticationState){
        userAuthenticationState.update {
            value
        }
    }
    fun getCurrentFirebaseUser():StateFlow<FirebaseUser?> = currentFirebaseUser
    fun updateCurrentFirebaseUser(user:FirebaseUser?){
        currentFirebaseUser.update {
            user
        }
    }
    fun signOut(){
        currentFirebaseUser.value = null
        userAuthenticationState.value = UserAuthenticationState.Unauthenticated
    }
    fun getEmail() = currentFirebaseUser.value?.email
    fun getUid() = currentFirebaseUser.value?.uid
    fun getName() = currentFirebaseUser.value?.displayName
    fun getPhotUri() = currentFirebaseUser.value?.photoUrl
    fun getUserFavoriteList() = userData.value?.favoriteWords?: emptyList()


}