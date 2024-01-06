package com.creamydark.avz.domain.usecase.postupdates

import com.creamydark.avz.data.repository.UpdatesFirestoreRepository
import com.creamydark.avz.domain.model.ResultType
import com.creamydark.avz.domain.model.UpdatesPostData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllPostUpdatesUseCase @Inject constructor(
    private val repository: UpdatesFirestoreRepository
) {
    suspend fun invoke():Flow<ResultType<List<UpdatesPostData>>>{
        return repository.getAllDocuments()
    }
}