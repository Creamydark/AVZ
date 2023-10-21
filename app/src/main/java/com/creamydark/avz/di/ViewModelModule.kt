package com.creamydark.avz.di

import androidx.hilt.navigation.compose.hiltViewModel
import com.creamydark.avz.domain.usecase.CheckIfUserDataExistUseCases
import com.creamydark.avz.domain.usecase.FirebaseAuthListenerUseCase
import com.creamydark.avz.domain.usecase.SignInUserUsingCredentialsUseCases
import com.creamydark.avz.domain.usecase.SignOutUseCase
import com.creamydark.avz.presentation.viewmodels.RootNavGraphViewModel
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ViewModelModule {

}