package com.creamydark.avz.di

import android.content.Context
import com.creamydark.avz.data.repository.GoogleClientSignInRepository
import com.creamydark.avz.data.repository.GoogleClientSignInRepositoryImpl
import com.creamydark.avz.data.repository.LessonsFirebaseRepository
import com.creamydark.avz.data.repository.LessonsFirebaseRepositoryImpl
import com.creamydark.avz.data.repository.UpdatesFirestoreRepository
import com.creamydark.avz.data.repository.UpdatesFirestoreRepositoryImpl
import com.creamydark.avz.data.repository.WordsFirestoreRepository
import com.creamydark.avz.data.repository.WordsFirestoreRepositoryImpl
import com.creamydark.avz.domain.some_api.JoYuriAuthenticationAPI
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {


    @Provides
    @Singleton
    fun authFirebase(): FirebaseAuth = Firebase.auth
    @Provides
    @Singleton
    fun fireStoreDB(): FirebaseFirestore = Firebase.firestore
    @Provides
    @Singleton
    fun firebaseStorage():FirebaseStorage = Firebase.storage
    @Provides
    @Singleton
    fun provideGoogleClientSignInRepository(
        googleSignInClient: GoogleSignInClient,
        joYuriAuthenticationAPI: JoYuriAuthenticationAPI
    ): GoogleClientSignInRepository {
        return GoogleClientSignInRepositoryImpl(
            auth = authFirebase(),
            googleSignInClient = googleSignInClient,
            joYuriAuthenticationAPI = joYuriAuthenticationAPI,
            db = fireStoreDB()
        )
    }

    @Provides
    @Singleton
    fun wordsFirestoreRepository(): WordsFirestoreRepository {
        return WordsFirestoreRepositoryImpl(
            fireStoreDB()
        )
    }

    @Provides
    @Singleton
    fun updatesFirestoreRepository(context: Context):UpdatesFirestoreRepository{
        return UpdatesFirestoreRepositoryImpl(firestore = fireStoreDB(), storage = firebaseStorage(), context = context)
    }
    @Provides
    @Singleton
    fun lessonLessonsFirebaseRepository(
        context: Context,
        joYuriAuthenticationAPI: JoYuriAuthenticationAPI
    ): LessonsFirebaseRepository{
        return LessonsFirebaseRepositoryImpl(
            context = context,
            joYuriAuthenticationAPI = joYuriAuthenticationAPI,
            storage = firebaseStorage(),
            firestore = fireStoreDB()
        )
    }
}