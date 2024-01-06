package com.creamydark.avz.domain.usecase.postupdates

import com.creamydark.avz.data.repository.UpdatesFirestoreRepository
import com.creamydark.avz.domain.model.ResultType
import com.creamydark.avz.domain.model.UpdatesPostData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class EditPostUpdateFirestoreUseCase @Inject constructor(
    private val updatesFirestoreRepository: UpdatesFirestoreRepository
){
    suspend fun invoke(data: UpdatesPostData): Flow<ResultType<String>> {
        return updatesFirestoreRepository.editPost(data)
    }
}