package com.noxinfinity.pdate.ui.view_models.edit_profile

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.noxinfinity.pdate.GetAllPurposeQuery
import com.noxinfinity.pdate.GetUserInfoQuery
import com.noxinfinity.pdate.data.repository.edit_profile.EditProfileRepository
import com.noxinfinity.pdate.data.repository.profile.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val profileRepository: ProfileRepository,
    private val editProfileRepository: EditProfileRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(EditProfileState())
    val uiState: StateFlow<EditProfileState> = _uiState

    init {
        fetchUser()
    }

    private fun fetchUser() {
        _uiState.value = EditProfileState(isFetching = true)
        try {
            viewModelScope.launch {
                val response = profileRepository.getProfile()

                response.fold(
                    onSuccess = {
                        _uiState.value = _uiState.value.copy(user = it!!)
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

        _uiState.value = EditProfileState(isFetching = false)
    }

    fun uploadAvatar(file: File) {
        _uiState.value = _uiState.value.copy(isLoading = true)
        viewModelScope.launch {
            editProfileRepository.uploadAvatar(file)
                .fold(
                    onSuccess = {
                        Log.d("UPLOAD_AVATAR SUCCESS", it.toString())
                        fetchUser()
                    },
                    onFailure = {
                        Log.d("UPLOAD_AVATAR Error", it.toString())
                    }
                )
        }
        _uiState.value = _uiState.value.copy(isLoading = false)
    }

    fun getAllPurpose() {
        _uiState.value = _uiState.value.copy(isLoading = true)

        viewModelScope.launch{
            val response = editProfileRepository.getAllPurpose()

            response.fold(
                onSuccess = {
                    Log.e("Get All Purpose", it.toString())
                    val items : List<GetUserInfoQuery.Purpose> = it.getAllPurpose.data?.map {  data ->
                        GetUserInfoQuery.Purpose(
                            id = data?.id ?: 1,
                            name = data?.name
                        )
                    } ?: listOf()
                    _uiState.value = _uiState.value.copy(
                        purposeList = items
                    )
                },
                onFailure = {
                    Log.e("Get All Purpose Error", it.toString())
                }
            )
        }

        _uiState.value = _uiState.value.copy(isLoading = false)
    }

    fun updatePurpose(items: List<Int>) {
        viewModelScope.launch{
            val response = editProfileRepository.updatePurpose(items)

            response.fold(
                onSuccess = {
                    fetchUser()
                },
                onFailure = {
                    Log.e("Get All Purpose Error", it.toString())
                }
            )
        }
    }

    fun uploadPicture(file: File) {
        viewModelScope.launch{
            val response = editProfileRepository.uploadPicture(file)
            Log.d("Upload Picture", response.toString())

            response.fold(
                onSuccess = {
                    fetchUser()
                },
                onFailure = {
                    Log.e("Get All Purpose Error", it.toString())
                }
            )

        }
    }

    fun deletePictureById(id: String) {
        viewModelScope.launch{
            val response = editProfileRepository.deletePictureById(id)

            response.fold(
                onSuccess = {
                    fetchUser()
                },
                onFailure = {
                    Log.e("Get All Purpose Error", it.toString())
                }
            )
        }
    }

    fun updatePictureById(file: File, id: String) {
        viewModelScope.launch{
            val response = editProfileRepository.updatePictureById(file, id)

            response.fold(
                onSuccess = {
                    fetchUser()
                },
                onFailure = {
                    Log.e("Get All Purpose Error", it.toString())
                }
            )
        }
    }

}