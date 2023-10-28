package com.creamydark.avz.data.datasource

import android.net.Uri
import com.creamydark.avz.domain.ResultType
import com.creamydark.avz.domain.model.AnnouncementPostData
import kotlinx.coroutines.flow.Flow
import java.sql.Timestamp

interface AnnouncementRepository {
    suspend fun post(username:String,caption:String,timestamp: Long,image:Uri?,profilePhoto:Uri?):Flow<ResultType<String>>
    suspend fun getAllAnnouncements():Flow<ResultType<List<AnnouncementPostData>>>
    suspend fun getPostImage(ref:String):ResultType<Uri>
}