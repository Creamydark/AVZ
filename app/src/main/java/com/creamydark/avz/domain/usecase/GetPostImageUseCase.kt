package com.creamydark.avz.domain.usecase

import android.net.Uri
import com.creamydark.avz.data.datasource.AnnouncementRepository
import com.creamydark.avz.domain.ResultType
import com.creamydark.avz.domain.model.AnnouncementPostData
import javax.inject.Inject

class GetPostImageUseCase @Inject constructor(
    private val repository: AnnouncementRepository
) {
    suspend fun invoke(path:String):ResultType<Uri?>{
        return repository.getPostImage(path)
    }
}