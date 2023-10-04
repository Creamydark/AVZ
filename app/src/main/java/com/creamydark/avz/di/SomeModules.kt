package com.creamydark.avz.di

import android.content.Context
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
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)


object SomeModules {

    @Provides
    fun provideContext(@ApplicationContext context: Context): Context {
        return context
    }

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