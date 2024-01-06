package com.creamydark.avz.domain.usecase.userclient

import com.creamydark.avz.data.repository.GoogleClientSignInRepository
import com.creamydark.avz.domain.model.ResultType
import com.creamydark.avz.domain.model.UserData
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class AddUserExtraDataUseCases @Inject constructor(
    private val googleClientSignInRepository: GoogleClientSignInRepository
) {
    suspend fun execute(
        data: UserData
    ):ResultType<String>{
        return googleClientSignInRepository.addUserExtraData(data)
    }
}