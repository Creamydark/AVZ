package com.creamydark.avz.di

import com.creamydark.avz.repository.UserFirebaseAccountRepository
import com.creamydark.avz.repository.UserFirebaseAccountRepositoryImpl
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


object SomeModules {
    @Provides
    @Singleton
    fun authFirebase():FirebaseAuth = Firebase.auth

    @Provides
    @Singleton
    fun fireStoreDB():FirebaseFirestore = Firebase.firestore

    @Provides
    @Singleton
    fun provideUserRepository():UserFirebaseAccountRepository {
        return UserFirebaseAccountRepositoryImpl(authFirebase(), fireStoreDB())
    }



}