package com.creamydark.avz.domain.usecase.wordsvocabulary

import com.creamydark.avz.data.repository.WordsFirestoreRepository
import com.creamydark.avz.domain.model.ResultType
import com.creamydark.avz.domain.model.WordsDataModel
import javax.inject.Inject

class UpdateWordsToFirestoreUseCase @Inject constructor(
    private val repository: WordsFirestoreRepository
) {
    suspend fun invoke(dataModel: WordsDataModel): ResultType<String> {
        return repository.updateWord(dataModel)
    }
}