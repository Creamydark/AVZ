package com.creamydark.avz.domain.usecase

import android.net.Uri
import com.creamydark.avz.data.datasource.AnnouncementRepository
import com.creamydark.avz.domain.ResultType
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UploadAnnouncementPostUseCase @Inject constructor(private val repository: AnnouncementRepository) {
    suspend fun invoke(username:String,caption:String,timestamp:Long,image: Uri?,profilePhoto:Uri?):Flow<ResultType<String>> = repository.post(username, caption, timestamp,image,profilePhoto)
}