package com.creamydark.avz.domain.usecase

import com.creamydark.avz.data.datasource.AnnouncementRepository
import com.creamydark.avz.domain.ResultType
import com.creamydark.avz.domain.model.AnnouncementPostData
import javax.inject.Inject

class DeleteUpdatePostFirestore @Inject constructor(
    private val repository: AnnouncementRepository
) {
    suspend fun invokeDelete(postData: AnnouncementPostData):ResultType<String>{
        return repository.deletePost(postData)
    }
}