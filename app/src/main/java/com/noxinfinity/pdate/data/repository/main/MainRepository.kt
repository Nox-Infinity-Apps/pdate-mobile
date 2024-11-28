package com.noxinfinity.pdate.data.repository.main

import android.util.Log
import com.apollographql.apollo.ApolloClient
import com.noxinfinity.pdate.GetUserInfoMutation
import com.noxinfinity.pdate.UpdateFCMTokenAndLocationMutation
import com.noxinfinity.pdate.data.data_source.local.SharedPreferencesManager
import com.noxinfinity.pdate.utils.helper.ApolloHelper
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val client: ApolloClient,
    private val sharedPreferencesManager: SharedPreferencesManager,
) {
    suspend fun getUserInfo() : Result<GetUserInfoMutation.LoginByGoogle?>  {
        return try {
            val token = sharedPreferencesManager.getToken()
            val response = client.mutation(GetUserInfoMutation(token!!)).execute()
            Result.success(response.data?.loginByGoogle)
        } catch (e : Exception) {
            Log.d("FETCH_USER REPO", e.message ?: "Unknown error")
            Result.failure(e)
        }
    }

    suspend fun updateFcmAndLocation(fcmToken: String?, lat: String?, lng: String?) : Result<String> {
        return try {
            val response = client.mutation(
                UpdateFCMTokenAndLocationMutation(
                    fcmNotificationToken = ApolloHelper.getOptionalParam(fcmToken),
                    lat = ApolloHelper.getOptionalParam(lat),
                    lng = ApolloHelper.getOptionalParam(lng),
                ),
            ).execute()
            Result.success(response.data?.updateFcmTokenAndLocation!!.message)
        } catch (e: Exception) {
            Log.d("UPDATE_FCM REPO", e.message ?: "Unknown error")
            Result.failure(e)
        }
    }
}