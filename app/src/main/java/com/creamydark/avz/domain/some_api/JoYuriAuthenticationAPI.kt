package com.creamydark.avz.domain.some_api

import com.creamydark.avz.domain.model.CurrentUserData
import com.creamydark.avz.domain.model.UserData
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject


class JoYuriAuthenticationAPI @Inject constructor(
) {
    val isClientAuthenticated = MutableStateFlow(false)

    val userData = MutableStateFlow<UserData?>(UserData())

    val currentUserData = MutableStateFlow<CurrentUserData?>(null)
    fun updateAuthenticatedState(state :Boolean){
        isClientAuthenticated.value = state
    }
    fun updateUserData(data: UserData?){
        userData.value = data
    }

    fun updateCurrentUserData(data: CurrentUserData?){
        currentUserData.value = data
    }
    fun signOut(){
        isClientAuthenticated.value = false
        userData.value = null
        currentUserData.value = null
    }
    fun getEmail() = currentUserData.value?.email
    fun getUid() = currentUserData.value?.uid
    fun getName() = currentUserData.value?.displayName
    fun getPhotUri() = currentUserData.value?.photoUri
    fun getFavoriteWords() = userData.value?.favoriteWords

}