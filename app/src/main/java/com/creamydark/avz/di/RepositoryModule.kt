package com.creamydark.avz.di

import com.creamydark.avz.data.datasource.TaskFireStoreSourceRepository
import com.creamydark.avz.data.datasource.TaskFireStoreSourceRepositoryImpl
import com.creamydark.avz.data.repository.GoogleClientSignInRepository
import com.creamydark.avz.data.repository.GoogleClientSignInRepositoryImpl
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
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
    fun provideGoogleClientSignInRepository(googleSignInClient: GoogleSignInClient): GoogleClientSignInRepository {
        return GoogleClientSignInRepositoryImpl(auth = authFirebase(), googleSignInClient = googleSignInClient)
    }

    @Provides
    @Singleton
    fun provideTaskFireStoreSourceRepository(): TaskFireStoreSourceRepository{
        return TaskFireStoreSourceRepositoryImpl(fireStoreDB())
    }

}