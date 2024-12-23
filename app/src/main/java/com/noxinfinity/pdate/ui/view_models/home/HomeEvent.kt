package com.noxinfinity.pdate.ui.view_models.home

sealed class HomeEvent {
    data class LoadMore(val offset: Int, val currentLat: Double, val currentLng: Double) : HomeEvent()
    data class Like(val id: String? = null) : HomeEvent()
    data class UnLike(val id: String? = null) : HomeEvent()
    data class Block(val id: String) : HomeEvent()
    data object Think : HomeEvent()
    data object PopUp : HomeEvent()
}