package com.creamydark.avz.domain.usecase.userclient

import com.creamydark.avz.data.repository.GoogleClientSignInRepository
import javax.inject.Inject

class SignOutUseCase @Inject constructor(private val googleClientSignInRepository: GoogleClientSignInRepository) {
    suspend fun signOut(){
        googleClientSignInRepository.signOut()
    }
}