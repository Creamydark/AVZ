package com.creamydark.avz.domain.usecase.userclient

import com.creamydark.avz.data.repository.GoogleClientSignInRepository
import com.creamydark.avz.domain.model.ResultType
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpdateUserFavoriteWordsUseCase @Inject constructor(
    private val googleClientSignInRepository: GoogleClientSignInRepository
) {
    suspend operator fun invoke(email:String,title:String):Flow<ResultType<String>>{
        return googleClientSignInRepository.addOrRemoveFavoriteWord(word = title, email = email)
    }
}