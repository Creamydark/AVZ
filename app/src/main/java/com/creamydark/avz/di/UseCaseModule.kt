package com.creamydark.avz.di

import com.creamydark.avz.data.datasource.TaskFireStoreSourceRepository
import com.creamydark.avz.data.repository.GoogleClientSignInRepository
import com.creamydark.avz.domain.usecase.AddUserExtraDataUseCases
import com.creamydark.avz.domain.usecase.CheckIfUserDataExistUseCases
import com.creamydark.avz.domain.usecase.FirebaseAuthListenerUseCase
import com.creamydark.avz.domain.usecase.GetUserExtraDataUsecase
import com.creamydark.avz.domain.usecase.SignInUserUsingCredentialsUseCases
import com.creamydark.avz.domain.usecase.SignOutUseCase
import com.creamydark.avz.domain.usecase.WordsFirestoreUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideGetUserExtraData(repository: TaskFireStoreSourceRepository):GetUserExtraDataUsecase{
        return GetUserExtraDataUsecase(repository)
    }

    @Provides
    @Singleton
    fun provideCheckIfUserSignedInListenerUseCase(repository: GoogleClientSignInRepository): FirebaseAuthListenerUseCase {
        return FirebaseAuthListenerUseCase(repository = repository)
    }
    @Provides
    @Singleton
    fun provideCheckIfUserDataExistUseCases(repository: TaskFireStoreSourceRepository): CheckIfUserDataExistUseCases {
        return CheckIfUserDataExistUseCases(repository)
    }
    @Provides
    @Singleton
    fun provideSignInUserUsingCredentialsUseCases(repository: GoogleClientSignInRepository): SignInUserUsingCredentialsUseCases{
        return SignInUserUsingCredentialsUseCases(repository)
    }

    @Provides
    @Singleton
    fun provideAddWordsToFirestoreUseCase(repository: TaskFireStoreSourceRepository): WordsFirestoreUseCase {
        return WordsFirestoreUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideAddUserExtraDataUseCases(repository: TaskFireStoreSourceRepository): AddUserExtraDataUseCases{
        return AddUserExtraDataUseCases(repository)
    }

    @Provides
    @Singleton
    fun provideSignOutUseCase(repository: GoogleClientSignInRepository): SignOutUseCase{
        return SignOutUseCase(repository)
    }
}