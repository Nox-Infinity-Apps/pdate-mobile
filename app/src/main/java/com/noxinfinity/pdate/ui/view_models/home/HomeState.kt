package com.noxinfinity.pdate.ui.view_models.home

import com.noxinfinity.pdate.data.models.home.ProfileData

data class HomeState(
    val profileList: List<ProfileData> = listOf(),
    val isLoading: Boolean = false,
    val offset: Int = 0,
)