package com.creamydark.avz.data.repository

import android.net.Uri
import com.creamydark.avz.domain.model.LessonData
import com.creamydark.avz.domain.model.ResultType
import kotlinx.coroutines.flow.Flow

interface LessonsFirebaseRepository {
    suspend fun createLesson(data : LessonData,uri: Uri?):Flow<ResultType<String>>
    suspend fun getLessonsList():Flow<List<LessonData>>
    suspend fun downloadPdfFromInternet(url:String,id:String):Flow<ResultType<Uri?>>
    suspend fun removeLesson(data: LessonData):Flow<ResultType<String>>
    suspend fun updateLesson(data: LessonData?):Flow<ResultType<String>>
}