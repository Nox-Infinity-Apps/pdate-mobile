package com.noxinfinity.pdate.ui.view_models.profile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.noxinfinity.pdate.data.repository.edit_profile.EditProfileRepository
import com.noxinfinity.pdate.data.repository.profile.ProfileRepository
import com.noxinfinity.pdate.ui.view_models.main.MainState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileRepository: ProfileRepository,

) : ViewModel() {
    private val _uiState = MutableStateFlow(ProfileState())
    val uiState: StateFlow<ProfileState> = _uiState


    fun fetchUser() {
        _uiState.value = ProfileState(isLoading = true)
        try {
            viewModelScope.launch {
                val response = profileRepository.getProfile()

                response.fold(
                    onSuccess = {
                        _uiState.value = _uiState.value.copy(user = it!!, isLoading = false)
                    },
                    onFailure = {
                        Log.d("FETCH_USER FAILED", it.message ?: "Unknown error")
                        fetchUser()
                    }
                )
            }
        } catch (e: Exception) {
            Log.d("FETCH_USER FAILED", e.message ?: "Unknown error")
        }
    }




}