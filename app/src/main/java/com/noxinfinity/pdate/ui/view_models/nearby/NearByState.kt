package com.noxinfinity.pdate.ui.view_models.nearby

import com.noxinfinity.pdate.SuggestUsersNearByQuery


data class NearByState(
    val nearByUsersList: List<SuggestUsersNearByQuery.SuggestedUsersNearBy?> = listOf(),
    val isLoading: Boolean = false,
)


