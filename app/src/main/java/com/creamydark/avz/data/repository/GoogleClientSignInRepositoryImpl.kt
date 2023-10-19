package com.creamydark.avz.data.repository

import android.util.Log
import com.creamydark.avz.domain.ResultType
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class GoogleClientSignInRepositoryImpl @Inject constructor(
    private val googleSignInClient: GoogleSignInClient,
    private val auth:FirebaseAuth
):GoogleClientSignInRepository {
    override suspend fun signOut() {
        googleSignInClient.signOut()
        auth.signOut()
    }

    override suspend fun authListener(): Flow<ResultType<String>> {
        return callbackFlow {
            trySend(
                ResultType.loading()
            )
            auth.addAuthStateListener {
                    user->
                if (user.currentUser!=null){
                    user.currentUser?.uid?.let {
                        uid ->
                        trySend(ResultType.success(data = uid))
                    }
                }else{
                    trySend(ResultType.error("Current User = NULL"))
                }
            }
            awaitClose()
        }
    }

    override suspend fun signInWithCredentials(account: GoogleSignInAccount): Flow<Result<String>> {
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