package com.creamydark.avz.data.datasource

import android.net.Uri
import com.creamydark.avz.domain.ResultType
import com.creamydark.avz.domain.model.AnnouncementPostData
import kotlinx.coroutines.flow.Flow

interface AnnouncementRepository {
    suspend fun post(displayName:String,emailUploader:String,caption:String,timestamp: Long,image:Uri?,profilePhoto:Uri?):Flow<ResultType<String>>
    suspend fun getAllAnnouncements():Flow<ResultType<List<AnnouncementPostData>>>
    suspend fun getPostImage(path:String):ResultType<Uri>
}