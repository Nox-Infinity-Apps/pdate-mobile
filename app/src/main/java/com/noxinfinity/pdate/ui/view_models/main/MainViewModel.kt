package com.noxinfinity.pdate.ui.view_models.main

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.noxinfinity.pdate.data.data_source.local.SharedPreferencesManager
import com.noxinfinity.pdate.data.repository.main.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val sharedPreferencesManager: SharedPreferencesManager,
) : ViewModel() {

    private val _uiState = MutableStateFlow<MainState>(
        value = MainState.Loading
    )
    val uiState: StateFlow<MainState> = _uiState

    fun fetchUser() {
        _uiState.value = MainState.Loading
        try {
            viewModelScope.launch {
                val result = mainRepository.getUserInfo()
                _uiState.value = result.fold(
                    onSuccess = {
                        Log.d("FETCH_USER", it.toString())
                        if(it?.user == null) {
                            MainState.Error(
                                "",
                                tokenTimeOut = true,
                            )
                        } else {
                            Log.d("FETCH_USER", it.message)
                            sharedPreferencesManager.saveAccessToken(it.accessToken)
                            MainState.Success(
                                isNew = (it).isNew,
                                user = (it).user
                            )
                        }
                    },
                    onFailure = {
                        Log.d("FETCH_USER", it.toString())
                        MainState.Error(
                            it.message ?: "Error Fetching user"
                        )
                    }
                )
            }
        } catch (e: Exception) {
            _uiState.value = MainState.Error(e.message ?: "Unknown error")
        }
    }

    fun updateFcmAndLocation(fcmToken: String?, lat: String?, lng: String?) {
        viewModelScope.launch {
            val result = mainRepository.updateFcmAndLocation(fcmToken, lat, lng)
            result.fold(
                onSuccess = {
                    Log.d("UPDATE_FCM", it)
                },
                onFailure = {
                    Log.d("UPDATE_FCM", it.message?: "Unknown error")
                }
            )
        }
    }
}