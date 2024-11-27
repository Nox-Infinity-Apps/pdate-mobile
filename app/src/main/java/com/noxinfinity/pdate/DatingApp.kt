package com.noxinfinity.pdate

import android.app.Application
import com.google.firebase.FirebaseApp
import dagger.hilt.android.HiltAndroidApp
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.client.logger.ChatLogLevel

@HiltAndroidApp
class DatingApp : Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)

        ChatClient.Builder(getString(R.string.stream_key), this)
            .logLevel(ChatLogLevel.ALL)
            .build()
    }
}