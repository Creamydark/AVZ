package com.creamydark.avz.domain.usecase

import com.creamydark.avz.data.datasource.TaskFireStoreSourceRepository
import com.creamydark.avz.domain.ResultType
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CheckIfUserDataExistUseCases @Inject constructor(
    private val repository: TaskFireStoreSourceRepository
) {
    suspend fun check(uid:String): Flow<ResultType<Boolean>> {
        return repository.checkUserDataExist(uid = uid)
    }
}