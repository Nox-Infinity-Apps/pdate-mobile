package com.noxinfinity.pdate.ui.view_models.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.noxinfinity.pdate.R
import com.noxinfinity.pdate.data.models.home.ProfileData
import com.noxinfinity.pdate.data.repository.home.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repo: HomeRepository
) : ViewModel() {
    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state

    init {
        loadMoreProfile()
    }

    fun onTriggerEvent(event: HomeEvent) {
        viewModelScope.launch {
            when (event) {
                is HomeEvent.LoadMoreProfile -> loadMoreProfile()
                is HomeEvent.LikeProfile -> likeProfile()
                is HomeEvent.DislikeProfile -> dislikeProfile()
                is HomeEvent.ThinkProfile -> thinkProfile()
            }
        }
    }

    private fun popUpProfile() {
        _state.value = _state.value.copy(
            profileList = _state.value.profileList.drop(1),
            isLoading = false
        )
        if (_state.value.profileList.size < 5) {
            loadMoreProfile()
        }
    }

    private fun thinkProfile() {
        popUpProfile()
    }

    private fun dislikeProfile() {
        popUpProfile()
    }

    private fun likeProfile() {
        popUpProfile()
    }

    private fun loadMoreProfile() {
        viewModelScope.launch {
            _state.value = _state.value.copy(
                isLoading = true
            )

            val list = repo.loadMoreProfile()

            _state.value = _state.value.copy(
                isLoading = false,
                profileList = _state.value.profileList + list,
            )

        }
    }
}


