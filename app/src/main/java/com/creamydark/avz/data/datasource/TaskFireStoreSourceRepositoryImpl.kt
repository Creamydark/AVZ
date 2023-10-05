package com.creamydark.avz.data.datasource

import com.creamydark.avz.domain.model.UserData
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class TaskFireStoreSourceRepositoryImpl @Inject constructor(
    private val db : FirebaseFirestore
):TaskFireStoreSourceRepository {
    override suspend fun getUserExtraData(uid: String): Flow<Result<UserData?>> {
        return callbackFlow {
            try {
                val c = db.collection("users").document(uid).get()
                c.addOnSuccessListener {
                    trySend(Result.success(it.toObject(UserData::class.java)))
                }
                close()
            }catch (e :Throwable){
                trySend(Result.failure(e))
                close()
            }
            awaitClose()
        }
    }

    override suspend fun addUserExtraData(uid: String, userData: UserData): Flow<Result<String>> {
        return callbackFlow {
            try {
                val c = db.collection("users").document(uid).set(userData)
                c.await()
                trySend(Result.success("Register Successfully"))
                close()
            }catch (e:Throwable){
                trySend(Result.failure(e))
                close()
            }
        }
    }



}