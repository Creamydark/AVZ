package com.creamydark.avz.data.repository

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import kotlinx.coroutines.flow.Flow

interface GoogleClientSignInRepository {
    suspend fun signOut()

    suspend fun checkIfUserAlreadySignedIn(): Flow<Boolean>

    suspend fun signInWithCredentials(account: GoogleSignInAccount):Flow<Result<String>>


    /*suspend fun getUserExtraData(uid:String): Flow<Result<UserData?>>*/
}