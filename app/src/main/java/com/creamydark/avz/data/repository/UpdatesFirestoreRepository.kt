package com.creamydark.avz.data.repository

import android.net.Uri
import com.creamydark.avz.domain.model.ResultType
import com.creamydark.avz.domain.model.UpdatesPostData
import kotlinx.coroutines.flow.Flow

interface UpdatesFirestoreRepository {
    suspend fun post(data: UpdatesPostData,postImage: Uri?):Flow<ResultType<String>>
    suspend fun getAllDocuments():Flow<ResultType<List<UpdatesPostData>>>
    suspend fun deletePost(data: UpdatesPostData): ResultType<String>
    suspend fun editPost(data: UpdatesPostData):Flow<ResultType<String>>
    suspend fun getAllDocumentsForWorkManager():Flow<List<UpdatesPostData>>


}