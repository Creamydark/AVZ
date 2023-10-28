package com.creamydark.avz.data.datasource

import android.net.Uri
import android.util.Log
import com.creamydark.avz.domain.ResultType
import com.creamydark.avz.domain.model.AnnouncementPostData
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import java.sql.Timestamp
import javax.inject.Inject
import javax.xml.transform.Result

class AnnouncementRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val firebaseStorage: FirebaseStorage
):AnnouncementRepository {
    override suspend fun post(
        username: String,
        caption: String,
        timestamp: Long,
        image: Uri?,
        profilePhoto: Uri?
    ): Flow<ResultType<String>> {
        return callbackFlow {
            try {
                val ref =firebaseStorage.reference.child("POSTS-ANNOUNCEMENTS/$username/$timestamp/image-post.jpg")
                val task = ref.putFile(image!!).await()
                trySend(ResultType.success("Image upload successfully"))
                if (task.task.isSuccessful){
                    val data = HashMap<String,Any>()
                    data["username"] = username
                    data["caption"] = caption
                    data["timestamp"] = timestamp
                    data["profilePhoto"] = profilePhoto.toString()
                    val db = firestore.collection("Announcements-Post")
                    db.document("$username-$timestamp").set(data).await()
                    trySend(ResultType.success("Upload Successfully"))
                }
                close()
            }catch (e:Throwable){
                trySend(ResultType.error(e))
                close()
            }
            awaitClose()
        }
    }

    override suspend fun getAllAnnouncements(): Flow<ResultType<List<AnnouncementPostData>>> {
        return callbackFlow {
            val ref = firestore.collection("Announcements-Post")
            val task = ref.addSnapshotListener { value, error ->
                val data = value?.toObjects(AnnouncementPostData::class.java)?: emptyList()
                trySend(ResultType.success(data))
            }
            awaitClose {
                task.remove()
            }
        }
    }

    override suspend fun getPostImage(path: String): ResultType<Uri> {
        return try {
            val ref = firebaseStorage.reference.child(path)
            val data = ref.downloadUrl.await()
            ResultType.success(data)
        }catch (e:Throwable){
            ResultType.error(e)
        }
    }


}