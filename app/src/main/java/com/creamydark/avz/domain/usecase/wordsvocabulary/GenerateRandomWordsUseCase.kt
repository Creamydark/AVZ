package com.creamydark.avz.domain.usecase.wordsvocabulary

import com.creamydark.avz.data.datasource.RandomWordsRepository
import com.creamydark.avz.domain.model.ResultType
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GenerateRandomWordsUseCase @Inject constructor(private val repository: RandomWordsRepository) {
    suspend fun invoke():Flow<ResultType<String?>>{
        return repository.requestRandomWords()
    }
}