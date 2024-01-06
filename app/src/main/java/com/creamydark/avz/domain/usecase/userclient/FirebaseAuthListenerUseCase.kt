package com.creamydark.avz.domain.usecase.userclient

import com.creamydark.avz.data.repository.GoogleClientSignInRepository
import javax.inject.Inject

class FirebaseAuthListenerUseCase @Inject constructor(private val repository: GoogleClientSignInRepository) {
    suspend fun invoke() = repository.authListener()
}