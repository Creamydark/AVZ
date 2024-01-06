package com.creamydark.avz.data.repository

import android.content.Context
import android.net.Uri
import androidx.core.net.toUri
import com.creamydark.avz.domain.model.LessonData
import com.creamydark.avz.domain.model.ResultType
import com.creamydark.avz.domain.some_api.JoYuriAuthenticationAPI
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.File
import java.io.FileOutputStream
import java.util.UUID
import javax.inject.Inject

class LessonsFirebaseRepositoryImpl @Inject constructor(

    private val firestore: FirebaseFirestore,
    private val storage: FirebaseStorage,
    private val context: Context,
    private val joYuriAuthenticationAPI: JoYuriAuthenticationAPI
) : LessonsFirebaseRepository {
    override suspend fun createLesson(data: LessonData, uri: Uri?): Flow<ResultType<String>> {
        return callbackFlow {
            try {
                trySend(ResultType.loading())
                if (data.title.isBlank() || data.description.isBlank() || data.subtitle.isBlank()) {
                    throw Exception("Please fill he text-fields")
                }
                val uuid: String = UUID.randomUUID().toString()
                val userEmail = joYuriAuthenticationAPI.getEmail() ?: "none"
                if (uri != null) {
                    val refStorage =
                        storage.reference.child("LESSONS/$userEmail/$uuid/pdf_file.pdf")
                    val taskStorage = refStorage.putFile(uri).await()
                    trySend(ResultType.success("Uploading PDF file..."))

                    if (taskStorage.task.isSuccessful) {
                        val fileLink = refStorage.downloadUrl.await()
                        val selectedData = data.copy(
                            id = uuid,
                            who_posted_email = userEmail,
                            pdf_link = fileLink.toString(),
                            who_posted_profilePhoto = joYuriAuthenticationAPI.getPhotUri().toString(),
                            who_posted_name = joYuriAuthenticationAPI.getName().toString()
                        )
                        val lessonRef = firestore.collection("LESSONS").document(uuid)
                        val lessonfttask = lessonRef.set(selectedData).await()
                        trySend(ResultType.success("published successfully"))
                        close()
                    } else {
                        trySend(ResultType.error(Exception(taskStorage.task.exception)))
                        close()
                    }

                } else {
                    trySend(ResultType.error(Exception("Null pdf file")))
                    close()
                }
            } catch (e: Exception) {
                trySend(ResultType.error(e))
                close()
            }
            awaitClose {

            }
        }
    }

    override suspend fun getLessonsList(): Flow<List<LessonData>> {
        return callbackFlow {
            val ref = firestore.collection("LESSONS")
            ref.addSnapshotListener { value, error ->
                val list = value?.toObjects<LessonData>() ?: emptyList()
                trySend(list)
            }
            awaitClose()
        }
    }

    override suspend fun downloadPdfFromInternet(url: String, id: String): Flow<ResultType<Uri?>> {
        return callbackFlow {
            try {
                val response = OkHttpClient().newCall(
                    Request.Builder()
                        .url(url)
                        .build()
                ).execute()
                if (response.isSuccessful) {
                    val body = response.body
                    if (body != null) {
                        val byteStream = body.byteStream()
                        val file = File(context.cacheDir, "$id.pdf")
                        val fileOutputStream = FileOutputStream(file)
                        try {
                            byteStream.use {
                                fileOutputStream.use {
                                    it.write(byteStream.readBytes())
                                }
                            }
                        } finally {
                            withContext(Dispatchers.IO) {
                                fileOutputStream.close()
                            }
                            trySend(ResultType.success(file.toUri()))
                            close()
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                trySend(
                    ResultType.error(e)
                )
                close()
            }
            awaitClose {

            }
        }
    }


    override suspend fun removeLesson(data: LessonData): Flow<ResultType<String>> {
        return callbackFlow {
            try {
                val refStorage = storage.reference.child("LESSONS/${data.who_posted_email}/${data.id}/pdf_file.pdf")
                refStorage.delete().await()
                val firestoreRef = firestore.collection("LESSONS").document(data.id).delete()
                firestoreRef.await()
                trySend(ResultType.success("Removed successfully"))
                close()
            }catch (e:Exception){
                trySend(ResultType.error(e))
                close()
            }
            awaitClose {

            }
        }
    }

    override suspend fun updateLesson(data: LessonData?): Flow<ResultType<String>> {
        return callbackFlow {
            try {
                if (data != null){
                    val ref = firestore.collection("LESSONS").document(data.id).set(data)
                    ref.await()
                    trySend(ResultType.success("Update Successful"))
                    close()
                }else{
                    throw Exception("Null lesson data")
                }
            }catch (e:Exception){
                trySend(ResultType.error(e))
                close()
            }
            awaitClose {
            }
        }
    }
}