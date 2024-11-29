package com.noxinfinity.pdate.ui.view_models.nearby

sealed class NearByEvent {
    data class LoadSuggestedList(val offset: Int, val currentLat: Double, val currentLng: Double) : NearByEvent()
}