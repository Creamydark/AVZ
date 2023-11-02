package com.creamydark.avz.data.datasource

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import com.creamydark.avz.domain.ResultType
import com.creamydark.avz.domain.model.AnnouncementPostData
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import java.io.ByteArrayOutputStream
import javax.inject.Inject

class AnnouncementRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val firebaseStorage: FirebaseStorage,
    private val context: Context
):AnnouncementRepository {
    override suspend fun post(
        displayName: String,
        emailUploader: String,
        caption: String,
        timestamp: Long,
        image: Uri?,
        profilePhoto: Uri?
    ): Flow<ResultType<String>> {
        return callbackFlow {
            try {
                val ref =firebaseStorage.reference.child("POSTS-ANNOUNCEMENTS/$emailUploader/$timestamp/image-post.jpg")
//                val task = ref.putFile(image!!).await()
                if (image != null){
                    val compressedImage = compressImage(image, 60,context)
                    val task = ref.putBytes(compressedImage).await()
                    trySend(ResultType.success("Image upload successfully"))
                    if (task.task.isSuccessful){
                        val data = HashMap<String,Any>()
                        data["displayName"] = displayName
                        data["emailUploader"] = emailUploader
                        data["caption"] = caption
                        data["timestamp"] = timestamp
                        data["profilePhoto"] = profilePhoto.toString()
                        val db = firestore.collection("Announcements-Post")
                        db.document("$emailUploader-$timestamp").set(data).await()
                        trySend(ResultType.success("Upload Successfully"))
                        close()
                    }
                }else{
                    throw Exception("Invalid Image")
                }
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
    private fun compressImage(imageUri: Uri, quality: Int,context: Context): ByteArray {
        val bitmap = BitmapFactory.decodeStream(context.contentResolver.openInputStream(imageUri))
        // Create a new bitmap with the desired quality
        val compressedBitmap = Bitmap.createScaledBitmap(bitmap, bitmap.width / 2, bitmap.height / 2, false)
        // Convert the compressed bitmap to a byte array
        val outputStream = ByteArrayOutputStream()
        compressedBitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)
        return outputStream.toByteArray()
    }
    fun decreaseImageUriSize(imageUri: Uri, newWidth: Int, newHeight: Int,context: Context): Uri {
        // Decode the image from the URI.
        val inputStream = context.contentResolver.openInputStream(imageUri)
        val bitmap = BitmapFactory.decodeStream(inputStream)

        // Create a new bitmap with the desired dimensions.
        val newBitmap = Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, false)

        // Compress the new bitmap.
        val byteArrayOutputStream = ByteArrayOutputStream()
        newBitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)

        // Encode the compressed bitmap to a byte array.
        val byteArray = byteArrayOutputStream.toByteArray()

        // Create a new URI from the byte array.

        return Uri.parse(byteArrayOutputStream.toString())
    }
}