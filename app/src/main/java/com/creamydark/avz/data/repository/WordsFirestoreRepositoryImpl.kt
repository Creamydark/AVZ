package com.creamydark.avz.data.repository

import com.creamydark.avz.domain.model.ResultType
import com.creamydark.avz.domain.model.WordsDataModel
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObjects
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import java.util.UUID
import javax.inject.Inject

class WordsFirestoreRepositoryImpl @Inject constructor(
    private val db:FirebaseFirestore
): WordsFirestoreRepository {
    override suspend fun deleteWord(data: WordsDataModel): ResultType<String> {
        return try {
            ResultType.loading()
            val ref = db.collection("VOCAVULARY-WORDS").document(data.id).delete()
            ref.await()
            ResultType.success("Delete Successfully")
        }catch (e:Exception){
            ResultType.error(e)
        }
    }
    override suspend fun getAllWordsList(): Flow<List<WordsDataModel>?> {
        return callbackFlow {
            val ref = db.collection("VOCAVULARY-WORDS").addSnapshotListener { value, error ->
                val list = value?.toObjects<WordsDataModel>()
                trySend(list)
            }
            awaitClose {
                ref.remove()
            }
        }
    }
    override suspend fun uploadWord(data: WordsDataModel): ResultType<String> {
        return try {
            val uid:String = UUID.randomUUID().toString()
            if (data.title.isBlank() || data.description.isBlank() || data.example.isBlank()){
                throw Exception("Please fill the fields")
            }
            val model = hashMapOf(
                "title" to data.title,
                "description" to data.description,
                "example" to data.example,
                "timestamp" to FieldValue.serverTimestamp(),
                "uploader" to data.uploader,
                "id" to uid
            )
            ResultType.loading()
            val ref = db.collection("VOCAVULARY-WORDS").document(uid).set(model)
            ref.await()
            ResultType.success("Word uploaded successfully")
        }catch (e:Exception){
            ResultType.error(e)
        }
    }
    override suspend fun updateWord(data: WordsDataModel): ResultType<String> {
        return try {
            val ref = db.collection("VOCAVULARY-WORDS").document(data.id).set(data)
            ref.await()
            ResultType.success("Update Successfully")
        }catch (e:Exception){
            ResultType.error(e)
        }
    }
}