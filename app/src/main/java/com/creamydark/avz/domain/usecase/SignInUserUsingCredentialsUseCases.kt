package com.creamydark.avz.domain.usecase

import com.creamydark.avz.data.repository.GoogleClientSignInRepository
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import kotlinx.coroutines.flow.Flow

class SignInUserUsingCredentialsUseCases(private val repository: GoogleClientSignInRepository) {
    suspend fun signIn(account: GoogleSignInAccount): Flow<Result<String>> {
        return repository.signInWithCredentials(account)
    }
}