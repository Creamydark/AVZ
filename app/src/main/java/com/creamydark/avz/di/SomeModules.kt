package com.creamydark.avz.di

import android.content.Context
import com.creamydark.avz.TextToSpeechManager
import com.creamydark.avz.data.datasource.RandomWordsAPI
import com.creamydark.avz.domain.some_api.JoYuriAuthenticationAPI
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)


object SomeModules {

    @Provides
    @Singleton
    fun provideRandomWordsRetrofitApi():Retrofit{
        return Retrofit.Builder()
            .baseUrl("https://api.api-ninjas.com/") // Replace with your API base URL
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideRandomWordsAPI():RandomWordsAPI{
        return provideRandomWordsRetrofitApi().create(RandomWordsAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideGoogleSignIn(context:Context):GoogleSignInClient{
        return GoogleSignIn.getClient(
                context, GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("774052194850-834g2cdv8inbaq6h8suv3cmr56947e7d.apps.googleusercontent.com")
                .requestEmail()
                .build())
    }

    @Provides
    @Singleton
    fun provideTextToSpeechManager(context: Context):TextToSpeechManager{
        return TextToSpeechManager(context = context)
    }


    @Provides
    @Singleton
    fun provideJoYuriAuthenticationAPI(auth: FirebaseAuth):JoYuriAuthenticationAPI{
        return JoYuriAuthenticationAPI(

        )
    }

    @Provides
    @Singleton
    fun provideGoogleLastSignedIn(context: Context):GoogleSignInAccount?{
        return GoogleSignIn.getLastSignedInAccount(context)
    }

    @Provides
    @Singleton
    fun provideContext(@ApplicationContext context: Context): Context {
        return context
    }


}