package com.creamydark.avz.repository

import com.creamydark.avz.datamodels.FirebaseAccountResponseData
import com.creamydark.avz.datamodels.ResultResponse
import kotlinx.coroutines.flow.Flow

interface UserFirebaseAccountRepository {

    suspend fun signInAccount(email: String,password: String):Flow<ResultResponse<String>>

    suspend fun signOut()

    suspend fun checkIfUserAlreadySignedIn():Flow<Boolean>

    suspend fun signUpAccount(
        email:String,
        password:String,
        name:String,
        userType:String
    ):Flow<FirebaseAccountResponseData>

}