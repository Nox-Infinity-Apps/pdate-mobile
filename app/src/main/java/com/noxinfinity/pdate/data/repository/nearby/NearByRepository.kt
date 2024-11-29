package com.noxinfinity.pdate.data.repository.nearby

import com.apollographql.apollo.ApolloClient
import com.noxinfinity.pdate.SuggestUsersNearByQuery
import javax.inject.Inject

class NearByRepository @Inject constructor(
    private val client: ApolloClient,
) {
    suspend fun getNearbyUsers(lat: Double, lng: Double, offset: Int) : Result<List<SuggestUsersNearByQuery.SuggestedUsersNearBy?>?> {
        return try {
            val response = client.query(SuggestUsersNearByQuery(lat, lng, offset)).execute()
            return Result.success(response.data?.suggestedUsersNearBy)
        } catch (e : Exception) {
            Result.failure(e)
        }
    }
}