package com.creamydark.avz.domain.usecase

import com.creamydark.avz.data.datasource.TaskFireStoreSourceRepository
import com.creamydark.avz.domain.ResultType
import com.creamydark.avz.domain.model.WordsDataModel
import kotlinx.coroutines.flow.Flow
import java.time.format.ResolverStyle
import javax.inject.Inject

class WordsFirestoreUseCase @Inject constructor(private val repository: TaskFireStoreSourceRepository) {
    suspend fun upload(date :WordsDataModel): Flow<ResultType<String>>{
        return repository.addWordsToFirestore(date)
    }

    suspend fun getAllWords():Flow<ResultType<List<WordsDataModel>>>{
        return repository.getWordsFromFirestore()
    }

}