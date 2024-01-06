package com.creamydark.avz.domain.usecase.postupdates

import com.creamydark.avz.data.repository.UpdatesFirestoreRepository
import com.creamydark.avz.domain.model.ResultType
import com.creamydark.avz.domain.model.UpdatesPostData
import javax.inject.Inject

class DeletePostUpdatesFirestoreUseCase @Inject constructor(
    private val repository: UpdatesFirestoreRepository
) {
    suspend fun invokeDelete(postData: UpdatesPostData): ResultType<String> {
        return repository.deletePost(postData)
    }
}