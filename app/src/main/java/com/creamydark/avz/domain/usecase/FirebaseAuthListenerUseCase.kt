package com.creamydark.avz.domain.usecase

import com.creamydark.avz.data.repository.GoogleClientSignInRepository
import com.creamydark.avz.domain.ResultType
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FirebaseAuthListenerUseCase @Inject constructor(private val repository: GoogleClientSignInRepository) {
    suspend fun invoke():Flow<ResultType<String>> = repository.authListener()
}