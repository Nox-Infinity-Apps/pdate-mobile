package com.noxinfinity.pdate.ui.view_models.edit_profile

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.noxinfinity.pdate.GetAllPurposeQuery
import com.noxinfinity.pdate.GetListGradeQuery
import com.noxinfinity.pdate.GetUserInfoQuery
import com.noxinfinity.pdate.data.repository.edit_profile.EditProfileRepository
import com.noxinfinity.pdate.data.repository.profile.ProfileRepository
import com.noxinfinity.pdate.type.Gender
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

    fun getAllHobbies() {
        _uiState.value = _uiState.value.copy(isLoading = true)

        viewModelScope.launch{
            val response = editProfileRepository.getAllHobbies()

            response.fold(
                onSuccess = {
                    Log.e("Get All Purpose", it.toString())
                    val items : List<GetUserInfoQuery.Hobby> = it.getAllHobbies.data?.map {  data ->
                        GetUserInfoQuery.Hobby(
                            id = data?.id ?: 0,
                            iconUrl = data?.iconUrl ?: "",
                            title = data?.title ?: "",
                        )
                    } ?: listOf()
                    _uiState.value = _uiState.value.copy(
                        hobbiesList = items
                    )
                },
                onFailure = {
                    Log.e("Get All Purpose Error", it.toString())
                }
            )
        }

        _uiState.value = _uiState.value.copy(isLoading = false)
    }

    fun updateHobbies(hobbies: List<Int>) {
        viewModelScope.launch{
            val response = editProfileRepository.updateUserInfo(
                hobbies = hobbies,
            )

            response.fold(
                onSuccess = {
                    fetchUser()
                },
                onFailure = {
                    Log.e("Update Hobbies Error", it.toString())
                }
            )
        }
    }

    fun getListGrade() {
        _uiState.value = _uiState.value.copy(isLoading = true)

        viewModelScope.launch{
            val response = editProfileRepository.getListGrade()

            response.fold(
                onSuccess = {
                    Log.e("Get All Purpose", it.toString())
                    val items : List<GetUserInfoQuery.Grade> = it.getListGrade?.map {  data ->
                        GetUserInfoQuery.Grade(
                            id = data?.id ?: 0,
                            name = data?.name ?: "",
                        )
                    } ?: listOf()
                    _uiState.value = _uiState.value.copy(
                        gradeList = items
                    )
                },
                onFailure = {
                    Log.e("Get All Purpose Error", it.toString())
                }
            )
        }

        _uiState.value = _uiState.value.copy(isLoading = false)
    }

    fun getListMajor() {
        _uiState.value = _uiState.value.copy(isLoading = true)

        viewModelScope.launch{
            val response = editProfileRepository.getListMajor()

            response.fold(
                onSuccess = {
                    Log.e("Get All Purpose", it.toString())
                    val items : List<GetUserInfoQuery.Major> = it.getListMajor?.map {  data ->
                        GetUserInfoQuery.Major(
                            id = data?.id ?: 0,
                            name = data?.name ?: "",
                            iconUrl = data?.iconUrl ?: ""
                        )
                    } ?: listOf()
                    _uiState.value = _uiState.value.copy(
                        majorList = items
                    )
                },
                onFailure = {
                    Log.e("Get All Purpose Error", it.toString())
                }
            )
        }

        _uiState.value = _uiState.value.copy(isLoading = false)
    }

    fun updateUser(gender: Gender? = null,
                      grade: Int? = null,
                      major: Int? = null,
                      bio: String? = null,
                      dob: String? = null,
                      email: String? = null,
                      fullName: String? = null,
                      hobbies: List<Int>? = null,) {
        viewModelScope.launch{
            val response = editProfileRepository.updateUserInfo(
                gender = gender,
                grade = grade,
                major = major,
                bio = bio,
                dob = dob,
                email = email,
                fullName = fullName,
                hobbies = hobbies
            )

            response.fold(
                onSuccess = {
                    Log.d("Update Hobbies Success", it.toString())
                },
                onFailure = {
                    Log.e("Update Hobbies Error", it.toString())
                }
            )
        }
    }

}