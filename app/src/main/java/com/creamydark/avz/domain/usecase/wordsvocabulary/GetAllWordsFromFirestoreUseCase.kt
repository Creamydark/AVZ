package com.creamydark.avz.domain.usecase.wordsvocabulary

import com.creamydark.avz.data.repository.WordsFirestoreRepository
import com.creamydark.avz.domain.model.WordsDataModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllWordsFromFirestoreUseCase @Inject constructor(
    private val repository: WordsFirestoreRepository
) {
    suspend fun invoke():Flow<List<WordsDataModel>?> = repository.getAllWordsList()
}