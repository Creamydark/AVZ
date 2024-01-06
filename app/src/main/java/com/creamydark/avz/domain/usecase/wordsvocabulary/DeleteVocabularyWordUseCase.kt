package com.creamydark.avz.domain.usecase.wordsvocabulary

import com.creamydark.avz.data.repository.WordsFirestoreRepository
import com.creamydark.avz.domain.model.ResultType
import com.creamydark.avz.domain.model.WordsDataModel
import javax.inject.Inject

class DeleteVocabularyWordUseCase @Inject constructor(
    private val wordsFirestoreRepository: WordsFirestoreRepository
) {
    suspend fun invokeDelete(data:WordsDataModel): ResultType<String> {
        return wordsFirestoreRepository.deleteWord(data)
    }
}