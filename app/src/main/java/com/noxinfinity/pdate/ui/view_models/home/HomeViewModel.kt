package com.noxinfinity.pdate.ui.view_models.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.noxinfinity.pdate.data.repository.home.HomeRepository
import com.noxinfinity.pdate.utils.helper.LocationHelper
import dagger.hilt.android.lifecycle.HiltViewModel
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
        when (event) {
            is HomeEvent.Like -> likeProfile(id = event.id)
            is HomeEvent.LoadMore -> loadMoreProfile()
            is HomeEvent.Think -> popUpProfile()
            is HomeEvent.UnLike -> dislikeProfile(id = event.id)
            is HomeEvent.Block -> blockProfile(id = event.id)
            else -> popUpProfile()
        }
    }


    private fun popUpProfile() {
        _state.value = _state.value.copy(
            profileList = _state.value.profileList.drop(1),
        )
        if (_state.value.profileList.size < 4 && !_state.value.isFetching) {
            _state.value = _state.value.copy(
                isFetching = true,
            )
            loadMoreProfile()
            _state.value = _state.value.copy(
                isFetching = false,
            )

        }
    }

    private fun blockProfile(id: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(
                isLoading = true,
            )
            repo.blockUser(id = id)
            _state.value = _state.value.copy(
                isLoading = false,
            )
        }
    }

    private fun dislikeProfile(id : String?) {
        viewModelScope.launch {
            _state.value = _state.value.copy(
                isLoading = true,
            )
            repo.dontCareUser(id = id ?: _state.value.profileList.first()!!.fcmId)
            _state.value = _state.value.copy(
                isLoading = false,
            )
        }
    }

    private fun likeProfile(id: String?) {
        viewModelScope.launch {
            _state.value = _state.value.copy(
                isLoading = true,
            )

            val name = _state.value.profileList.first()?.fullName ?: ""
            val response = repo.likeUser(id ?: _state.value.profileList.first()!!.fcmId)

            response.fold(
                onSuccess = {
                    Log.d("HomeViewModel", "likeProfile: ${it.like}")
                    if(it.like?.isMatched != 0) {
                        _state.value = _state.value.copy(
                            isDialogShow = true,
                            dialogData = DialogData(
                                name = name,
                                conversationId = (it.like?.conversationId ?: "") as String
                            )
                        )
                    }
                },
                onFailure = {
                    Log.d("HomeViewModel Error", "likeProfile: $it")
                }
            )

            _state.value = _state.value.copy(
                isLoading = false,
            )
        }
    }

    private fun loadMoreProfile() {
        viewModelScope.launch {
            _state.value = _state.value.copy(
                isLoading = true,
            )

            val response = repo.getSuggestUser(
                currentLat = LocationHelper.lat,
                currentLng = LocationHelper.lng,
                offset = _state.value.offset
            )

            response.fold(
                onSuccess = {
                    _state.value = _state.value.copy(
                        profileList = _state.value.profileList + it,
                        offset = _state.value.offset + 1,
                    )
                },
                onFailure = {
                    Log.d("HomeViewModel", "loadMoreProfile: $it")
                    _state.value = _state.value.copy(
                        isError = true,
                    )
                }
            )

            _state.value = _state.value.copy(
                isLoading = false,
            )
        }
    }

    fun onDismissDialog() {
        _state.value = _state.value.copy(
            isDialogShow = false,
        )
    }
}


