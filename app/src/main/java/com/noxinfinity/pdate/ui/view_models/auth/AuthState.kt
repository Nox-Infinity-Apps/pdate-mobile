package com.noxinfinity.pdate.ui.view_models.auth

data class AuthState(
    val isLoading: Boolean = true,
    val isSuccess: Boolean = false,
    val isLoggedIn : Boolean = false,
    val isReloadAvatar : Boolean = false,
)
