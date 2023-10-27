package com.creamydark.avz.domain.usecase

import com.creamydark.avz.data.datasource.TaskFireStoreSourceRepository
import com.creamydark.avz.domain.ResultType
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpdateFavoriteWordsUseCase @Inject constructor(
    private val repository: TaskFireStoreSourceRepository
) {
    suspend operator fun invoke(email:String,title:String):Flow<ResultType<String>>{
        return repository.updateFavoriteWords(email,title)
    }
}