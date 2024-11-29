package com.noxinfinity.pdate.ui.view_models.main

import com.noxinfinity.pdate.LoginByGoogleMutation

sealed class MainState {
    data class Success(
        val user: LoginByGoogleMutation.User,
        val isNew: Boolean,
    ) : MainState()

    data class Error(val message: String, val tokenTimeOut: Boolean = false) : MainState()

    data object Loading : MainState()
}

