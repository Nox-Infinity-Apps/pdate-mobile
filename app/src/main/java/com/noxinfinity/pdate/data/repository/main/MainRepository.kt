package com.noxinfinity.pdate.data.repository.main

import com.apollographql.apollo.ApolloClient
import com.noxinfinity.pdate.GetUserInfoMutation
import com.noxinfinity.pdate.data.data_source.local.SharedPreferencesManager
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
            Result.failure(e)
        }
    }
}