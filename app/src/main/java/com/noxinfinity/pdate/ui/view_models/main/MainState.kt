package com.noxinfinity.pdate.ui.view_models.main

import com.noxinfinity.pdate.GetUserInfoMutation

sealed class MainState {
    data class Success(
        val user: GetUserInfoMutation.User,
        val isNew: Boolean,
    ) : MainState()

    data class Error(val message: String, val tokenTimeOut: Boolean = false) : MainState()

    data object Loading : MainState()
}

