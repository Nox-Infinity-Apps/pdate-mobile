package com.noxinfinity.pdate.services

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService

class MyFirebaseMessagingService : FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("FCM", "New FCM Token: $token")

        sendTokenToServer(token)
    }

    private fun sendTokenToServer(token: String) {
    }
}