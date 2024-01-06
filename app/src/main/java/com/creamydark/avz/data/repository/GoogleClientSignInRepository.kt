package com.creamydark.avz.data.repository

import com.creamydark.avz.domain.model.ResultType
import com.creamydark.avz.domain.model.UserData
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import kotlinx.coroutines.flow.Flow

interface GoogleClientSignInRepository {
    suspend fun signOut()
    suspend fun authListener()
    suspend fun signInFirebaseAuthWithCredentials(account: GoogleSignInAccount):Flow<Result<String>>

    suspend fun addOrRemoveFavoriteWord(email:String,word:String):Flow<ResultType<String>>

    suspend fun addUserExtraData(data: UserData):ResultType<String>


}