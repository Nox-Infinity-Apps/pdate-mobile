package com.noxinfinity.pdate.data.data_source.local

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject

class SharedPreferencesManager @Inject constructor(
    private val context: Context
) {
    companion object {
        private const val PREFS_NAME = "user_prefs"
        private const val KEY_USER_TOKEN = "user_token"
        private const val KEY_ACCESS_TOKEN = "access_token"
    }

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun saveToken(token: String) {
        sharedPreferences.edit().putString(KEY_USER_TOKEN, token).apply()
    }

    fun getToken(): String? {
        return sharedPreferences.getString(KEY_USER_TOKEN, null)
    }

    fun clearToken() {
        sharedPreferences.edit().remove(KEY_USER_TOKEN).apply()
    }

    fun saveAccessToken(token: String) {
        sharedPreferences.edit().putString(KEY_ACCESS_TOKEN, token).apply()
    }

    fun getAccessToken(): String? {
        return sharedPreferences.getString(KEY_ACCESS_TOKEN, null)
    }
}
