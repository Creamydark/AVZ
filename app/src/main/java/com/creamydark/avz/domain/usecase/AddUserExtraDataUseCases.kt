package com.creamydark.avz.domain.usecase

import com.creamydark.avz.data.datasource.TaskFireStoreSourceRepository
import com.creamydark.avz.domain.model.UserData
import kotlinx.coroutines.flow.Flow

class AddUserExtraDataUseCases(private val repository: TaskFireStoreSourceRepository) {
    suspend fun execute(uid:String,data:UserData):Flow<Result<String>>{
        return repository.addUserExtraData(uid = uid,userData = data)
    }
}