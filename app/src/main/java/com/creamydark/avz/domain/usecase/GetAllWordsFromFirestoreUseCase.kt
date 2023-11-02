package com.creamydark.avz.domain.usecase

import com.creamydark.avz.data.datasource.TaskFireStoreSourceRepository
import com.creamydark.avz.domain.model.WordsDataModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllWordsFromFirestoreUseCase @Inject constructor(
    private val repository: TaskFireStoreSourceRepository
) {
    suspend fun invoke():Flow<List<WordsDataModel>> = repository.getWordsFromFirestore()
}