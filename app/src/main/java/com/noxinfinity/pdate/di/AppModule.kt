package com.noxinfinity.pdate.di

import android.content.Context
import com.apollographql.apollo.ApolloClient
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.noxinfinity.pdate.R
import com.noxinfinity.pdate.data.data_source.local.SharedPreferencesManager
import com.noxinfinity.pdate.data.repository.home.HomeRepository
import com.noxinfinity.pdate.data.repository.chat.ChatRepository
import com.noxinfinity.pdate.ui.view_models.chat.ChatViewModel
import com.noxinfinity.pdate.utils.Constants
import com.noxinfinity.pdate.utils.helper.JWTHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.models.User
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideSharedPreferencesManager(
        @ApplicationContext context: Context
    ): SharedPreferencesManager {
        return SharedPreferencesManager(context)
    }

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
    fun provideApolloClient(sharedPreferencesManager: SharedPreferencesManager) : ApolloClient {
        return ApolloClient.Builder()
            .serverUrl(Constants.BASE_URL)
            .addHttpHeader(
                "Authorization", "Bearer ${sharedPreferencesManager.getAccessToken()}"
            )
            .build()
    }


}