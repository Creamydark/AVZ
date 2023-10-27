package com.creamydark.avz.data.repository

import android.net.Uri
import com.creamydark.avz.domain.ResultType
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

interface GoogleClientSignInRepository {
    suspend fun signOut()
    suspend fun authListener()
    suspend fun signInFirebaseAuthWithCredentials(account: GoogleSignInAccount):Flow<Result<String>>
}