package com.creamydark.avz.di

import com.creamydark.avz.data.datasource.AnnouncementRepository
import com.creamydark.avz.data.datasource.RandomWordsRepository
import com.creamydark.avz.data.datasource.TaskFireStoreSourceRepository
import com.creamydark.avz.data.repository.GoogleClientSignInRepository
import com.creamydark.avz.domain.usecase.AddUserExtraDataUseCases
import com.creamydark.avz.domain.usecase.AddWordsFirestoreUseCase
import com.creamydark.avz.domain.usecase.FirebaseAuthListenerUseCase
import com.creamydark.avz.domain.usecase.GenerateRandomWordsUseCase
import com.creamydark.avz.domain.usecase.GetAllWordsFromFirestoreUseCase
import com.creamydark.avz.domain.usecase.GetPostImageUseCase
import com.creamydark.avz.domain.usecase.GetUserExtraDataUsecase
import com.creamydark.avz.domain.usecase.SignInUserUsingCredentialsUseCases
import com.creamydark.avz.domain.usecase.SignOutUseCase
import com.creamydark.avz.domain.usecase.UpdateFavoriteWordsUseCase
import com.creamydark.avz.domain.usecase.UploadAnnouncementPostUseCase
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
    fun provideUploadAnnouncementPostUseCase(repository: AnnouncementRepository): UploadAnnouncementPostUseCase {
        return UploadAnnouncementPostUseCase(repository)
    }
    @Provides
    @Singleton
    fun provideGenerateRandomWordsUseCase(repository: RandomWordsRepository): GenerateRandomWordsUseCase {
        return GenerateRandomWordsUseCase(repository)
    }
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
    fun provideSignInUserUsingCredentialsUseCases(repository: GoogleClientSignInRepository): SignInUserUsingCredentialsUseCases{
        return SignInUserUsingCredentialsUseCases(repository)
    }

    @Provides
    @Singleton
    fun provideGetAllWordsFromFirestoreUseCase(repository: TaskFireStoreSourceRepository): GetAllWordsFromFirestoreUseCase {
        return GetAllWordsFromFirestoreUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideAddWordsToFirestoreUseCase(repository: TaskFireStoreSourceRepository): AddWordsFirestoreUseCase {
        return AddWordsFirestoreUseCase(repository)
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
    @Provides
    @Singleton
    fun provideAddFavoriteWordsUseCase(repository: TaskFireStoreSourceRepository): UpdateFavoriteWordsUseCase {
        return UpdateFavoriteWordsUseCase(repository)
    }

    @Provides
    @Singleton
    fun getGetPostImageUseCase(repository: AnnouncementRepository): GetPostImageUseCase {
        return GetPostImageUseCase(repository)
    }
}