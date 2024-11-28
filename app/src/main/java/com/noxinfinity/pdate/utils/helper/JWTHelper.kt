package com.noxinfinity.pdate.utils.helper

import android.util.Base64
import org.json.JSONObject

class JWTHelper {
    companion object{
        fun decodeJwtPayLoad(jwt: String) : JSONObject? {
            return try {
                val parts = jwt.split(".")
                if(parts.size < 2) return null

                val decodedBytes = Base64.decode(parts[1], Base64.URL_SAFE)
                val decodedString = String(decodedBytes)

                JSONObject(decodedString)
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }

        fun getStreamToken(jwt: String) : String {
            val payload = decodeJwtPayLoad(jwt) ?: return ""
            return (payload as JSONObject).optString("streamToken", "")
        }

        fun isJwtExpired(jwt: String) : Boolean {
            val payload = decodeJwtPayLoad(jwt) ?: true;
            val exp = (payload as JSONObject).optLong("exp", 0L)
            val currentTime = System.currentTimeMillis() / 1000
            return exp <= currentTime

        }
    }
}