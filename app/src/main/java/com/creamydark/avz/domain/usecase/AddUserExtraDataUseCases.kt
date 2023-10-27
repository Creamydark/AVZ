package com.creamydark.avz.domain.usecase

import com.creamydark.avz.data.datasource.TaskFireStoreSourceRepository
import com.creamydark.avz.domain.ResultType
import com.creamydark.avz.domain.model.UserData
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import javax.inject.Inject

class AddUserExtraDataUseCases @Inject constructor(private val repository: TaskFireStoreSourceRepository) {
    suspend fun execute(
        data: UserData
    ):Flow<ResultType<String>>{
        return repository.addUserExtraData(
            data
        )
    }
}