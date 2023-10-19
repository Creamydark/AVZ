package com.creamydark.avz.data.datasource

import com.creamydark.avz.domain.ResultType
import com.creamydark.avz.domain.model.UserData
import com.creamydark.avz.domain.model.WordsDataModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import java.time.LocalDate
import javax.inject.Inject

class TaskFireStoreSourceRepositoryImpl @Inject constructor(
    private val db : FirebaseFirestore,
    private val auth: FirebaseAuth
):TaskFireStoreSourceRepository {


    override suspend fun getUserExtraData(): Flow<Result<UserData?>> {
        return callbackFlow {
            try {
                auth.currentUser?.uid?.let {
                    uid ->
                    val task = db.collection("users").document(uid).get()
                    task.addOnSuccessListener {
                        val data : UserData? = it.toObject(UserData::class.java)
                        trySend(Result.success(data))
                    }
                }
            }catch (e :Throwable){
                trySend(Result.failure(e))
                close()
            }
            awaitClose()
        }
    }

    override suspend fun checkUserDataExist(uid: String): Flow<ResultType<Boolean>> {
        return callbackFlow {
            trySend(ResultType.loading())
            db.collection("users").document(uid).get().addOnSuccessListener {
                document ->
                if (document.exists()){
                    trySend(ResultType.success(true))
                }else{
                    trySend(ResultType.success(false))
                }
            }.addOnFailureListener {
                trySend(ResultType.error(it.message.toString()))
            }
            awaitClose()
        }
    }



    override suspend fun addUserExtraData(
        bday: LocalDate,
        userType: Boolean
    ): Flow<ResultType<String>> {
        return callbackFlow {
            try {
                auth.currentUser?.let {userrrr ->
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
            trySend(ResultType.loading())
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

    override suspend fun getWordsFromFirestore(): Flow<ResultType<List<WordsDataModel>>> {
        return callbackFlow {
            val query = db.collection("wordsss").get()
            query.addOnSuccessListener {
                val list = it.toObjects(WordsDataModel::class.java)
                trySend(ResultType.success(list))
            }
            query.addOnFailureListener {
                trySend(ResultType.error(it.message.toString()))
            }
            awaitClose()
        }
    }


}