package com.creamydark.avz.di

import android.content.Context
import com.creamydark.avz.data.datasource.TaskFireStoreSourceRepository
import com.creamydark.avz.data.datasource.TaskFireStoreSourceRepositoryImpl
import com.creamydark.avz.data.repository.GoogleClientSignInRepository
import com.creamydark.avz.data.repository.GoogleClientSignInRepositoryImpl
import com.creamydark.avz.domain.some_api.JoYuriAuthenticationAPI
import com.google.android.gms.auth.api.signin.GoogleSignIn
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
    fun provideTaskFireStoreSourceRepository(context: Context,joYuriAuthenticationAPI: JoYuriAuthenticationAPI): TaskFireStoreSourceRepository{
        return TaskFireStoreSourceRepositoryImpl( db = fireStoreDB(),joYuriAuthenticationAPI)
    }

}