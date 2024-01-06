package com.creamydark.avz.di

import com.creamydark.avz.data.datasource.RandomWordsRepository
import com.creamydark.avz.data.repository.WordsFirestoreRepository
import com.creamydark.avz.data.repository.GoogleClientSignInRepository
import com.creamydark.avz.data.repository.UpdatesFirestoreRepository
import com.creamydark.avz.domain.usecase.wordsvocabulary.AddWordsFirestoreUseCase
import com.creamydark.avz.domain.usecase.postupdates.DeletePostUpdatesFirestoreUseCase
import com.creamydark.avz.domain.usecase.postupdates.EditPostUpdateFirestoreUseCase
import com.creamydark.avz.domain.usecase.wordsvocabulary.DeleteVocabularyWordUseCase
import com.creamydark.avz.domain.usecase.userclient.FirebaseAuthListenerUseCase
import com.creamydark.avz.domain.usecase.wordsvocabulary.GenerateRandomWordsUseCase
import com.creamydark.avz.domain.usecase.postupdates.GetAllPostUpdatesUseCase
import com.creamydark.avz.domain.usecase.wordsvocabulary.GetAllWordsFromFirestoreUseCase
import com.creamydark.avz.domain.usecase.userclient.GetUserExtraDataUsecase
import com.creamydark.avz.domain.usecase.userclient.SignInUserUsingCredentialsUseCases
import com.creamydark.avz.domain.usecase.userclient.SignOutUseCase
import com.creamydark.avz.domain.usecase.postupdates.PostNewUpdateUseCase
import com.creamydark.avz.domain.usecase.userclient.UpdateUserFavoriteWordsUseCase
import com.creamydark.avz.domain.usecase.wordsvocabulary.UpdateWordsToFirestoreUseCase
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
    fun editPostUpdateFirestoreUseCase(repository: UpdatesFirestoreRepository): EditPostUpdateFirestoreUseCase {
        return EditPostUpdateFirestoreUseCase(updatesFirestoreRepository = repository)
    }
    @Provides
    @Singleton
    fun DeleteUpdatePostFirestoreUseCase(repository: UpdatesFirestoreRepository): DeletePostUpdatesFirestoreUseCase {
        return DeletePostUpdatesFirestoreUseCase(repository)
    }

    @Provides
    @Singleton
    fun DeleteVocabularyWordUseCase(repository: WordsFirestoreRepository): DeleteVocabularyWordUseCase {
        return com.creamydark.avz.domain.usecase.wordsvocabulary.DeleteVocabularyWordUseCase(
            repository
        )
    }

    @Provides
    @Singleton
    fun provideUploadAnnouncementPostUseCase(repository: UpdatesFirestoreRepository): PostNewUpdateUseCase {
        return PostNewUpdateUseCase(repository)
    }
    @Provides
    @Singleton
    fun provideGenerateRandomWordsUseCase(repository: RandomWordsRepository): GenerateRandomWordsUseCase {
        return GenerateRandomWordsUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideCheckIfUserSignedInListenerUseCase(repository: GoogleClientSignInRepository): FirebaseAuthListenerUseCase {
        return FirebaseAuthListenerUseCase(repository = repository)
    }
    @Provides
    @Singleton
    fun provideSignInUserUsingCredentialsUseCases(repository: GoogleClientSignInRepository): SignInUserUsingCredentialsUseCases {
        return SignInUserUsingCredentialsUseCases(repository)
    }

    @Provides
    @Singleton
    fun provideGetAllWordsFromFirestoreUseCase(repository: WordsFirestoreRepository): GetAllWordsFromFirestoreUseCase {
        return GetAllWordsFromFirestoreUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideAddWordsToFirestoreUseCase(repository: WordsFirestoreRepository): AddWordsFirestoreUseCase {
        return AddWordsFirestoreUseCase(repository)
    }


    @Provides
    @Singleton
    fun provideSignOutUseCase(repository: GoogleClientSignInRepository): SignOutUseCase {
        return SignOutUseCase(repository)
    }


    @Provides
    @Singleton
    fun getAllAnnouncementsUseCase(repository: UpdatesFirestoreRepository): GetAllPostUpdatesUseCase {
        return GetAllPostUpdatesUseCase(repository= repository)
    }
    @Provides
    @Singleton
    fun updateWordsToFirestoreUseCase(repository: WordsFirestoreRepository): UpdateWordsToFirestoreUseCase {
        return UpdateWordsToFirestoreUseCase(repository= repository)
    }
    @Provides
    @Singleton
    fun updateUserFavoriteWordsUseCase(repository: GoogleClientSignInRepository): UpdateUserFavoriteWordsUseCase {
        return UpdateUserFavoriteWordsUseCase(repository)
    }

    @Provides
    @Singleton
    fun GetUserExtraDataUsecase(repository: WordsFirestoreRepository): GetUserExtraDataUsecase {
        return GetUserExtraDataUsecase(repository= repository)
    }


}