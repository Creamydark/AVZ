package com.creamydark.avz.repository

import com.creamydark.avz.datamodels.FirebaseAccountResponseData
import com.creamydark.avz.datamodels.ResultResponse
import com.creamydark.avz.datamodels.UserDataModel
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class UserFirebaseAccountRepositoryImpl(
    private val auth : FirebaseAuth,
    private val db : FirebaseFirestore,
):UserFirebaseAccountRepository {
    override suspend fun signInWithGoogle(account: GoogleSignInAccount): Result<Boolean> {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        return try {
            auth.signInWithCredential(credential).await()
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun signInAccount(
        email: String,
        password: String
    ): Flow<FirebaseAccountResponseData> {
        return callbackFlow {
            trySend(FirebaseAccountResponseData(isLoading = true))
            try {
                val c = auth.signInWithEmailAndPassword(email, password)
                c.await()
                trySend(FirebaseAccountResponseData(isSuccessful = "Sign in successful"))
            }catch (e :Throwable){
                trySend(FirebaseAccountResponseData(error = "${e.localizedMessage}"))
            }
            awaitClose()
        }
    }

    override suspend fun signOut() {
        auth.signOut()
    }

    override suspend fun checkIfUserAlreadySignedIn(): Flow<Boolean> {
        return callbackFlow {
            auth.addAuthStateListener {
                trySend(it.currentUser!=null)
            }
            awaitClose()
        }
    }

    override suspend fun signUpAccount(
        email: String,
        password: String,
        name: String,
        userType: String
    ): Flow<FirebaseAccountResponseData> {
        return callbackFlow {
            trySend(FirebaseAccountResponseData(isLoading = true))
            try {
                auth.createUserWithEmailAndPassword(email, password).await()
                try {
                    auth.currentUser?.uid?.let {uid ->
                        val user = UserDataModel(userType = userType, id = uid, name = name)
                        db.collection("users").add(user).await()
                        trySend(FirebaseAccountResponseData(isSuccessful = "Sucessfully Signed in"))
                    }
                    close()
                }catch (e:Throwable){
                    trySend(FirebaseAccountResponseData(error = e.localizedMessage, isLoading = false))
                    close()
                }
            }catch (e:Throwable){
                trySend(FirebaseAccountResponseData(error = e.localizedMessage, isLoading = false))
            }
            awaitClose()
        }
    }




    /*override suspend fun signUpAccount(
        email: String,
        password: String,
        userType: Boolean,
        name: String,
        subject: String,
        reponse: (ResultResponse<String>) -> Unit
    ) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("googleaccreport", "createUserWithEmail:success")
                    val user = auth.currentUser
                    auth.currentUser?.uid?.let { uuuuiid ->
                        val userdata = UserDataModel(
                            userType, uuuuiid, name, subject
                        )
                        // Add a new document with a generated ID
                        db.collection("users")
                            .add(userdata)
                            .addOnSuccessListener { documentReference ->
                                Log.d(
                                    "googleaccreport",
                                    "DocumentSnapshot added with ID: ${documentReference.id}"
                                )
                                reponse(ResultResponse.Success(""))
                            }
                            .addOnFailureListener { e ->
                                Log.w("googleaccreport", "Error adding document", e)
                                reponse(ResultResponse.Error(""))
                            }
                    }
                } else {
                    // If sign in fails, display a message to the user.
                    reponse(ResultResponse.Error(""))
                }
            }
    }*/
}