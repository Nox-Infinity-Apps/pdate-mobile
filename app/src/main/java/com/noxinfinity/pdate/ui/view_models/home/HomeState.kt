package com.noxinfinity.pdate.ui.view_models.home

import com.noxinfinity.pdate.SuggestedUsersQuery


data class HomeState(
    val profileList: List<SuggestedUsersQuery.SuggestedUser?> = listOf(),
    val offset: Int = 0,
    val isError: Boolean = false,
    val isLoading: Boolean = false,
)