package com.creamydark.avz.repository

import com.creamydark.avz.datamodels.FirebaseAccountResponseData
import com.creamydark.avz.datamodels.ResultResponse
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import kotlinx.coroutines.flow.Flow

interface UserFirebaseAccountRepository {
    suspend fun signInWithGoogle(account: GoogleSignInAccount): Result<Boolean>

    suspend fun signInAccount(email: String,password: String):Flow<FirebaseAccountResponseData>

    suspend fun signOut()

    suspend fun checkIfUserAlreadySignedIn():Flow<Boolean>

    suspend fun signUpAccount(
        email:String,
        password:String,
        name:String,
        userType:String
    ):Flow<FirebaseAccountResponseData>

}