package com.creamydark.avz.data.datasource

import android.provider.ContactsContract.CommonDataKinds.Email
import com.creamydark.avz.domain.ResultType
import com.creamydark.avz.domain.model.UserData
import com.creamydark.avz.domain.model.WordsDataModel
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import java.time.format.ResolverStyle

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