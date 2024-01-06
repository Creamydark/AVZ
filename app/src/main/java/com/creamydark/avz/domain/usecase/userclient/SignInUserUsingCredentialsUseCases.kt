package com.creamydark.avz.domain.usecase.userclient

import com.creamydark.avz.data.repository.GoogleClientSignInRepository
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SignInUserUsingCredentialsUseCases @Inject constructor(private val repository: GoogleClientSignInRepository) {
    suspend fun signIn(account: GoogleSignInAccount): Flow<Result<String>> {
        return repository.signInFirebaseAuthWithCredentials(account)
    }
}