package com.creamydark.avz.data.datasource

import com.creamydark.avz.domain.model.UserData
import kotlinx.coroutines.flow.Flow

interface TaskFireStoreSourceRepository {
    suspend fun getUserExtraData(uid:String):Flow<Result<UserData?>>
    suspend fun addUserExtraData(uid: String,userData: UserData):Flow<Result<String>>
}