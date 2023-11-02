package com.creamydark.avz.data.datasource

import com.creamydark.avz.domain.ResultType
import com.creamydark.avz.domain.model.UserData
import com.creamydark.avz.domain.model.WordsDataModel
import kotlinx.coroutines.flow.Flow

interface TaskFireStoreSourceRepository {
    suspend fun getUserExtraData(email:String):Flow<ResultType<UserData?>>
    suspend fun addUserExtraData(data :UserData):Flow<ResultType<String>>
    suspend fun addWordsToFirestore(data :WordsDataModel?):Flow<ResultType<String>>
    suspend fun getWordsFromFirestore():Flow<List<WordsDataModel>>
    suspend fun updateFavoriteWords(
        email: String,
        title:String
    ): Flow<ResultType<String>>
}