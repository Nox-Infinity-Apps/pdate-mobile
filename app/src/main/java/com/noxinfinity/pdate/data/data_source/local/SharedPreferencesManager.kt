package com.noxinfinity.pdate.data.data_source.local

import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesManager(context: Context) {
    companion object {
        private const val PREFS_NAME = "user_prefs"
        private const val KEY_USER_TOKEN = "user_token"
    }

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun saveToken(token: String) {
        sharedPreferences.edit().putString(KEY_USER_TOKEN, token).apply()
    }

    fun getToken(): String? {
        return sharedPreferences.getString(KEY_USER_TOKEN, null)
    }

    fun clearToken() {
        sharedPreferences.edit().remove(KEY_USER_TOKEN).apply()
    }
}
