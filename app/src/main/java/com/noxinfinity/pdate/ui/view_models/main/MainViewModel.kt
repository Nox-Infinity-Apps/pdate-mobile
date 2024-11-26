package com.noxinfinity.pdate.ui.view_models.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.noxinfinity.pdate.data.repository.main.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository,
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
                            MainState.Success(
                                isNew = (it).isNew,
                                user = (it).user
                            )
                        }
                    },
                    onFailure = {
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
}