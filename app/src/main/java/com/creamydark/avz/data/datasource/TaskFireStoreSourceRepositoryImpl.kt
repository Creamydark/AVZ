package com.creamydark.avz.data.datasource

import android.util.Log
import com.creamydark.avz.domain.ResultType
import com.creamydark.avz.domain.model.UserData
import com.creamydark.avz.domain.model.WordsDataModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import java.time.LocalDate
import javax.inject.Inject

class TaskFireStoreSourceRepositoryImpl @Inject constructor(
    private val db : FirebaseFirestore,
    private val auth: FirebaseAuth
):TaskFireStoreSourceRepository {



    override suspend fun getUserExtraData(uid: String): Flow<ResultType<UserData>> {
        return callbackFlow {
            trySend(ResultType.loading())
            val collection = db.collection("users").document(uid)
            val reg = collection.addSnapshotListener { value, error ->
                value?.let {
                    valuedawd->
                    val data = valuedawd.toObject<UserData>()
                    if (data != null){
                        trySend(
                            ResultType.success(data = data)
                        )
                        Log.d("TaskFireStoreSourceRepositoryImpl", "getUserExtraData:${data.name} ${data.isStudent}")
                    }else{
                        trySend(
                            ResultType.error(error?.message.toString())
                        )
                    }
                }
            }
            awaitClose {
                reg.remove()
            }
        }
    }

    override suspend fun checkUserDataExist(uid: String): Flow<ResultType<Boolean>> {
        return callbackFlow {
            val collection = db.collection("users")
            val reg = collection.document(uid).addSnapshotListener { value, error ->
                value?.let {
                    trySend(ResultType.success(it.exists()))
                }
            }
            awaitClose {
                reg.remove()
            }

        }
    }



    override suspend fun addUserExtraData(
        bday: LocalDate,
        userType: Boolean
    ): Flow<ResultType<String>> {
        return callbackFlow {
            try {
                auth.currentUser?.let {
                        userrrr ->
                    val uid = userrrr.uid
                    val name = userrrr.displayName
                    val email = userrrr.email
                    val data = UserData(
                        email = email,
                        birthday = bday.toString(),
                        isStudent = userType,
                        name = name
                    )
                    val c = db.collection("users").document(uid).set(data)
                    c.await()
                    trySend(ResultType.success("Register Successfully"))
                }
                close()
            }catch (e:Throwable){
                trySend(ResultType.error(e.message.toString()))
                close()
            }
        }
    }

    override suspend fun addWordsToFirestore(data: WordsDataModel): Flow<ResultType<String>> {
        return callbackFlow {
            try {
                val shet = db.collection("wordsss").document(data.title).set(data)
                shet.await()
                trySend(ResultType.success("Upload Successfully"))
                close()
            }catch (e :Throwable){
                trySend(ResultType.error(e.message.toString()))
                close()
            }
            awaitClose()
        }
    }

    override suspend fun getWordsFromFirestore(): Flow<List<WordsDataModel>> {
        return callbackFlow {
            val collection = db.collection("wordsss")
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


}