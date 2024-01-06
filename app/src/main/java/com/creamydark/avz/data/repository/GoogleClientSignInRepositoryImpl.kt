package com.creamydark.avz.data.repository

import android.util.Log
import com.creamydark.avz.domain.model.ResultType
import com.creamydark.avz.domain.model.UserData
import com.creamydark.avz.domain.some_api.JoYuriAuthenticationAPI
import com.creamydark.avz.enozienum.UserAuthenticationState
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
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
            if (currentUser != null){
                joYuriAuthenticationAPI.updateCurrentFirebaseUser(currentUser)
                currentUser.email?.let {
                    email->
                    val collection = db.collection("users").document(email)
                    collection.addSnapshotListener { value, error ->
                        val userData = value?.toObject(UserData::class.java)
                        if (userData==null){
                            joYuriAuthenticationAPI.updateUserAuthenticationState(UserAuthenticationState.OnRegisterState)
                        }else{
                            joYuriAuthenticationAPI.updateUserData(userData)
                            joYuriAuthenticationAPI.updateUserAuthenticationState(UserAuthenticationState.Authenticated)
                        }
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

    override suspend fun addOrRemoveFavoriteWord(
        email: String,
        word: String
    ): Flow<ResultType<String>> {
        return callbackFlow {
            val ref = db.collection("users").document(email)
            ref.get().addOnSuccessListener { document ->
                val currentList = document.get("favoriteWords") as? ArrayList<String> ?: ArrayList()
                if (word !in currentList) {
                    currentList.add(word)
                } else {
                    currentList.remove(word)
                }
                ref.update("favoriteWords", currentList).addOnSuccessListener {
                    trySend(ResultType.success("$word added to favorites"))
                }
            }
            close()
            awaitClose {

            }
        }
    }

    override suspend fun addUserExtraData(data: UserData): ResultType<String> {
        return try {
            if (data.email == null){
                throw Exception("Invalid email")
            }
            db.collection("users").document(data.email).set(data).await()
            ResultType.success("Registered successfully")
        }catch (e:Exception){
            ResultType.error(e)
        }
    }


}