package com.creamydark.avz.domain.usecase

import com.creamydark.avz.data.repository.GoogleClientSignInRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CheckIfUserSignedInListenerUseCase (private val repository: GoogleClientSignInRepository) {
    suspend fun invoke():Flow<Boolean> = repository.checkIfUserAlreadySignedIn()
}