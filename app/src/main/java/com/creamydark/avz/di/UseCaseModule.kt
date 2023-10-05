package com.creamydark.avz.di

import com.creamydark.avz.data.datasource.TaskFireStoreSourceRepository
import com.creamydark.avz.data.repository.GoogleClientSignInRepository
import com.creamydark.avz.domain.usecase.CheckIfUserSignedInListenerUseCase
import com.creamydark.avz.domain.usecase.GetUserExtraDataUsecase
import com.creamydark.avz.domain.usecase.SignInUserUsingCredentialsUseCases
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
    fun provideCheckIfUserSignedInListenerUseCase(repository: GoogleClientSignInRepository): CheckIfUserSignedInListenerUseCase {
        return CheckIfUserSignedInListenerUseCase(repository = repository)
    }

    @Provides
    @Singleton
    fun provideSignInUserUsingCredentialsUseCases(repository: GoogleClientSignInRepository): SignInUserUsingCredentialsUseCases{
        return SignInUserUsingCredentialsUseCases(repository)
    }
}