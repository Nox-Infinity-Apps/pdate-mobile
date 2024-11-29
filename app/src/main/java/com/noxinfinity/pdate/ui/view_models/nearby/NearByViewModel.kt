package com.noxinfinity.pdate.ui.view_models.nearby

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.noxinfinity.pdate.data.repository.home.HomeRepository
import com.noxinfinity.pdate.data.repository.nearby.NearByRepository
import com.noxinfinity.pdate.ui.view_models.home.HomeState
import com.noxinfinity.pdate.utils.helper.LocationHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NearByViewModel @Inject constructor(
    private val repo: NearByRepository
) : ViewModel() {

    private val _state = MutableStateFlow(NearByState())
    val state: StateFlow<NearByState> = _state

    init {
        getNearbyUsers()
    }

    suspend fun onTriggerEvent(event: NearByEvent) {
        when (event) {
            is NearByEvent.LoadSuggestedList -> repo.getNearbyUsers(event.currentLat, event.currentLng, event.offset)
        }
    }

    private fun getNearbyUsers() {
        viewModelScope.launch {
            val data = repo.getNearbyUsers(
                lat = LocationHelper.lat,
                lng = LocationHelper.lng,
                offset = 0
            )
            _state.value = _state.value.copy(
                nearByUsersList = data.getOrNull() ?: listOf()
            )
        }
    }
}