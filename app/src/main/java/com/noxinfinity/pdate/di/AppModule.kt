package com.noxinfinity.pdate.di

import android.content.Context
import com.apollographql.apollo.ApolloClient
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.noxinfinity.pdate.R
import com.noxinfinity.pdate.data.repository.home.HomeRepository
import com.noxinfinity.pdate.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.getstream.chat.android.client.ChatClient
import javax.annotation.Signed
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideGoogleSignInClient(@ApplicationContext context: Context) : GoogleSignInClient{
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        return GoogleSignIn.getClient(context, gso)
    }

    @Provides
    @Singleton
    fun provideHomeRepository() : HomeRepository = HomeRepository()

    @Provides
    @Singleton
    fun provideApolloClient() : ApolloClient {
        return ApolloClient.Builder()
            .serverUrl(Constants.BASE_URL)
            .build()
    }

//    @Provides
//    @Singleton
//    fun provideChatClient() : ChatClient {
//
//    }
}