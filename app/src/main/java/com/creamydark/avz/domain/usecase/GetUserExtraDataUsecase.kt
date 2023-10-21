package com.creamydark.avz.domain.usecase

import com.creamydark.avz.data.datasource.TaskFireStoreSourceRepository
import com.creamydark.avz.domain.ResultType
import com.creamydark.avz.domain.model.UserData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserExtraDataUsecase @Inject constructor(private val repo : TaskFireStoreSourceRepository) {
    suspend fun execute(uid:String):Flow<ResultType<UserData>> = repo.getUserExtraData(uid)
}