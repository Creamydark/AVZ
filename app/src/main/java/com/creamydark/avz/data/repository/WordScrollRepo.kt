package com.creamydark.avz.data.repository

import android.util.Log
import com.creamydark.avz.domain.model.ResponseWordScrollSnapShot
import com.creamydark.avz.domain.model.ScrollableItem
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class WordScrollRepo {
    private val db = Firebase.firestore

    fun getAllWords(itemCallback:(ResponseWordScrollSnapShot)->Unit){
        db.collection("wordsss")
            .get()
            .addOnCompleteListener {
                result ->
                if (result.isSuccessful){
                    itemCallback(ResponseWordScrollSnapShot(items = result.result.toObjects(ScrollableItem::class.java), errorMessage = "Success"))
                }
            }.addOnFailureListener {
                result ->
                itemCallback(ResponseWordScrollSnapShot(items = emptyList(), errorMessage = result.message.toString()))
            }

    }
    fun addWords(
        item: ScrollableItem
    ){
        db.collection("wordsss")
            .add(item)
            .addOnSuccessListener { documentReference ->
                Log.d("googleaccreport", "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w("googleaccreport", "Error adding document", e)
            }
    }
}