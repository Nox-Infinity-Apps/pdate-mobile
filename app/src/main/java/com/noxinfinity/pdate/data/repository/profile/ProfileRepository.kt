package com.noxinfinity.pdate.data.repository.profile

import android.util.Log
import com.apollographql.apollo.ApolloClient
import com.noxinfinity.pdate.GetUserInfoQuery
import javax.inject.Inject

class ProfileRepository @Inject constructor(
    private val apolloClient: ApolloClient
) {
    suspend fun getProfile(): Result<GetUserInfoQuery.Data1? >{
        return try {
            val response = apolloClient.query(GetUserInfoQuery()).execute()

            Result.success(response.data?.getUserInfo?.onUserInfoSuccessResponse?.data)
        } catch (e : Exception) {
            Log.d("FETCH_USER REPO", e.message ?: "Unknown error")
            Result.failure(e)
        }

    }

}