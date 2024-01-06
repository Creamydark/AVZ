package com.creamydark.avz.data.repository

import com.creamydark.avz.domain.model.ResultType
import com.creamydark.avz.domain.model.WordsDataModel
import kotlinx.coroutines.flow.Flow

interface WordsFirestoreRepository {
    suspend fun deleteWord(
        data:WordsDataModel
    ): ResultType<String>
    suspend fun getAllWordsList(): Flow<List<WordsDataModel>?>
    suspend fun uploadWord(data: WordsDataModel): ResultType<String>
    suspend fun updateWord(data: WordsDataModel): ResultType<String>


}