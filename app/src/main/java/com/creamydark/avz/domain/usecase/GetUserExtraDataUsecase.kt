package com.creamydark.avz.domain.usecase

import com.creamydark.avz.data.datasource.TaskFireStoreSourceRepository
import com.creamydark.avz.domain.model.UserData
import kotlinx.coroutines.flow.Flow

class GetUserExtraDataUsecase (private val repo : TaskFireStoreSourceRepository) {
    suspend fun execute(uid:String):Flow<Result<UserData?>>{
        return repo.getUserExtraData(uid)
    }
}