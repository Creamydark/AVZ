package com.creamydark.avz.data.datasource

import com.creamydark.avz.domain.ResultType
import com.creamydark.avz.domain.model.UserData
import com.creamydark.avz.domain.model.WordsDataModel
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface TaskFireStoreSourceRepository {
    suspend fun getUserExtraData(uid: String):Flow<ResultType<UserData>>
    suspend fun checkUserDataExist(uid:String):Flow<ResultType<Boolean>>
    suspend fun addUserExtraData(bday:LocalDate,userType:Boolean):Flow<ResultType<String>>
    suspend fun addWordsToFirestore(data :WordsDataModel):Flow<ResultType<String>>
    suspend fun getWordsFromFirestore():Flow<List<WordsDataModel>>
}