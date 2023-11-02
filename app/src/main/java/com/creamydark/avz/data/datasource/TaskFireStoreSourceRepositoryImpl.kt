package com.creamydark.avz.data.datasource

import com.creamydark.avz.domain.ResultType
import com.creamydark.avz.domain.model.UserData
import com.creamydark.avz.domain.model.WordsDataModel
import com.creamydark.avz.domain.some_api.JoYuriAuthenticationAPI
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class TaskFireStoreSourceRepositoryImpl @Inject constructor(
    private val db : FirebaseFirestore,
    private val joYuriAuthenticationAPI: JoYuriAuthenticationAPI
):TaskFireStoreSourceRepository {


    override suspend fun getUserExtraData(email:String): Flow<ResultType<UserData?>> {
        return callbackFlow {
            val collection = db.collection("users").document(email)
            val reg = collection.addSnapshotListener { value, error ->
                val data = value?.toObject<UserData>()
                trySend(ResultType.success(data))
            }
            awaitClose{
                reg.remove()
            }
        }
    }

    override suspend fun addUserExtraData(data: UserData): Flow<ResultType<String>> {
        return callbackFlow {
            trySend(ResultType.loading())
            val email = joYuriAuthenticationAPI.getEmail()
            try {
                val collection = db.collection("users")
                val document = collection.document(email!!).set(data)
                document.await()
                trySend(ResultType.success("Successfully Update"))
                close()
            }catch (e:Throwable){
                trySend(
                    ResultType.error(e)
                )
                close()
            }
            awaitClose ()
        }
    }

    override suspend fun addWordsToFirestore(data: WordsDataModel?): Flow<ResultType<String>> {
        return callbackFlow {
            try {
                if (data!=null){
                    val shet = db.collection("Vocabulary-Words").document().set(data)
                    shet.await()
                    trySend(ResultType.success("Upload Successfully"))
                    close()
                }else{
                    throw Exception("Text-Fields cannot be blank or invalid")
                }
            }catch (e :Throwable){
                trySend(ResultType.error(e))
                close()
            }
            awaitClose()
        }
    }
    override suspend fun getWordsFromFirestore(): Flow<List<WordsDataModel>> {
        return callbackFlow {
            val collection = db.collection("Vocabulary-Words")
            val reg = collection.addSnapshotListener { value, error ->
                value?.let {
                    querySnapshot ->
                    val data = querySnapshot.toObjects(WordsDataModel::class.java)
                    trySend(data)
                }
            }
            awaitClose{
                reg.remove()
            }
        }
    }

    override suspend fun updateFavoriteWords(email: String, title: String): Flow<ResultType<String>> {
        return callbackFlow {
            val favoriteWords = joYuriAuthenticationAPI.getFavoriteWords()?:emptyList()
            val newFavoriteWords = favoriteWords.toMutableList()
            try {
                if (!newFavoriteWords.contains(title)){
                    newFavoriteWords.add(title)
                    val collection = db.collection("users").document(email)
                    val data = hashMapOf(
                        "favoriteWords" to newFavoriteWords
                    )
                    val reg = collection.update("favoriteWords",newFavoriteWords)
                    reg.await()
                    trySend(ResultType.success("Successfully Updated"))
                    close()
                }else{
                    newFavoriteWords.remove(title)
                    val collection = db.collection("users").document(email)
                    val data = hashMapOf(
                        "favoriteWords" to newFavoriteWords
                    )
                    val reg = collection.update("favoriteWords",newFavoriteWords)
                    reg.await()
                    trySend(ResultType.success("Successfully Updated"))
                    close()
                }
            }catch (e :Throwable){
                trySend(ResultType.error(e))
                close()
            }
            awaitClose {  }
        }
    }


}