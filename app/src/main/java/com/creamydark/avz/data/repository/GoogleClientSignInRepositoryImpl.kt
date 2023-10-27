package com.creamydark.avz.data.repository

import android.util.Log
import com.creamydark.avz.domain.model.CurrentUserData
import com.creamydark.avz.domain.model.UserData
import com.creamydark.avz.domain.some_api.JoYuriAuthenticationAPI
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class GoogleClientSignInRepositoryImpl @Inject constructor(
    private val googleSignInClient: GoogleSignInClient,
    private val auth:FirebaseAuth,
    private val db : FirebaseFirestore,
    private val joYuriAuthenticationAPI: JoYuriAuthenticationAPI
):GoogleClientSignInRepository {
    override suspend fun signOut() {
        joYuriAuthenticationAPI.signOut()
        googleSignInClient.signOut()
        auth.signOut()
    }

    override suspend fun authListener() {
        auth.addAuthStateListener {
                auth->
            val currentUser = auth.currentUser
            if (auth.currentUser != null){
                joYuriAuthenticationAPI.updateAuthenticatedState(true)
                val displayName = currentUser?.displayName
                val email = currentUser?.email
                val uid = currentUser?.uid
                val photoUri = currentUser?.photoUrl
                val currentUserData = CurrentUserData(
                    displayName, email, uid, photoUri
                )
                joYuriAuthenticationAPI.updateCurrentUserData(currentUserData)
                email?.let {
                    val collection = db.collection("users").document(email)
                    val reg = collection.addSnapshotListener { value, error ->
                        val data = value?.toObject<UserData>()
                        joYuriAuthenticationAPI.updateUserData(data)
                    }
                }
            }
        }
    }

    override suspend fun signInFirebaseAuthWithCredentials(account: GoogleSignInAccount): Flow<Result<String>> {
        return callbackFlow {
            try {
                val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                auth.signInWithCredential(credential).await()
                trySend(Result.success("Sign in Sucessfully"))
                close()
            }catch (e :Throwable){
                Log.d("GoogleClientSignInRepositoryImpl", "signInWithCredentials: ${e.message}")
                trySend(Result.failure(e))
                close()
            }
            awaitClose()
        }
    }

}