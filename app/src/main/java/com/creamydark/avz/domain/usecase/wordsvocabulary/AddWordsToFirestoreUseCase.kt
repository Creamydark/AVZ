package com.creamydark.avz.domain.usecase.wordsvocabulary

import com.creamydark.avz.data.repository.WordsFirestoreRepository
import com.creamydark.avz.domain.model.ResultType
import com.creamydark.avz.domain.model.WordsDataModel
import javax.inject.Inject

class AddWordsFirestoreUseCase @Inject constructor(private val repository: WordsFirestoreRepository) {
    suspend fun upload(data :WordsDataModel): ResultType<String> {
        return repository.uploadWord(data)
    }
}