package com.creamydark.avz.data.repository

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import com.creamydark.avz.domain.model.ResultType
import com.creamydark.avz.domain.model.UpdatesPostData
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.io.ByteArrayOutputStream
import java.util.UUID
import javax.inject.Inject

class UpdatesFirestoreRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val storage: FirebaseStorage,
    private val context: Context
):UpdatesFirestoreRepository {
    override suspend fun post(data: UpdatesPostData, postImage: Uri?): Flow<ResultType<String>> {
        return callbackFlow {
            try {
                val uuid: String = UUID.randomUUID().toString()
                val storageRef = storage.reference.child("POSTS-UPDATES/${data.emailUploader}/$uuid/image-post.jpg")
                if (postImage!=null){
                    val postImgTask = storageRef.putBytes(compressImage(imageUri = postImage, context = context)).await()
                    if (postImgTask.task.isSuccessful){
                        val postImgLink = postImgTask.storage.downloadUrl.await()
                        trySend(ResultType.success("Posting step 1/2"))
                        val firestoreRef = firestore.collection("UPDATES-POST")
                        val model = hashMapOf<String,Any>(
                            "displayName" to data.displayName,
                            "emailUploader" to data.emailUploader,
                            "caption" to data.caption,
                            "timestamp" to FieldValue.serverTimestamp(),
                            "profilePhoto" to data.profilePhoto,
                            "imagePostLink" to postImgLink,
                            "id" to uuid,
                        )
                        firestoreRef.document(uuid).set(model)
                            .addOnSuccessListener {
                                trySend(ResultType.success("Posted successfully"))
                                close()
                            }
                            .addOnFailureListener {
                                throw it
                            }
                    }else{
                        throw postImgTask.error?:Exception("Unknown Error")
                    }
                }else{
                    throw Exception("Invalid image")
                }
            }catch (e:Exception){
                trySend(ResultType.error(e))
                close()
            }
            awaitClose {

            }
        }
    }

    override suspend fun getAllDocuments(): Flow<ResultType<List<UpdatesPostData>>> {
        return callbackFlow {
            val ref = firestore.collection("UPDATES-POST")
                .addSnapshotListener { value, error ->
                    val list = value?.toObjects<UpdatesPostData>()?: emptyList()
                    trySend(ResultType.success(list))
                }
            awaitClose {
                ref.remove()
            }
        }
    }

    override suspend fun deletePost(data: UpdatesPostData): ResultType<String> {
        return try {
            val ref = firestore
                .collection("UPDATES-POST")
                .document(data.id)
                .delete()
            ref.await()
            ResultType.success("Post successfully deleted")
        }catch (e:Exception){
            ResultType.error(e)
        }
    }

    override suspend fun editPost(data: UpdatesPostData): Flow<ResultType<String>> {
        return callbackFlow {
            val job =  launch {
                val ref = firestore.collection("UPDATES-POST")
                ref.document(data.id).update("caption",data.caption)
                    .addOnSuccessListener {
                        trySend(ResultType.success("Update successfully"))
                        close()
                    }
                    .addOnFailureListener {
                        trySend(ResultType.error(it))
                        close()
                    }
            }
            awaitClose {
                job.cancel()
            }
        }
    }

    override suspend fun getAllDocumentsForWorkManager(): Flow<List<UpdatesPostData>> {
        return callbackFlow {
            val ref = firestore.collection("UPDATES-POST")
//                .whereGreaterThan("timestamp",currentDate)
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .limit(1)
                .addSnapshotListener { value, error ->
                    val list = value?.toObjects<UpdatesPostData>()
                    trySend(list?: emptyList())
                }
            awaitClose {  }
        }
    }

    private fun compressImage(imageUri: Uri,context: Context): ByteArray {
        val bitmap = BitmapFactory.decodeStream(context.contentResolver.openInputStream(imageUri))
        // Create a new bitmap with the desired quality
        val compressedBitmap = Bitmap.createScaledBitmap(bitmap, bitmap.width / 2, bitmap.height / 2, false)
        // Convert the compressed bitmap to a byte array
        val outputStream = ByteArrayOutputStream()
        val quality = if(bitmap.height>=300||bitmap.width>=300){
            70
        }else{
            100
        }
        compressedBitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)
        return outputStream.toByteArray()
    }
}