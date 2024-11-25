package com.noxinfinity.pdate.ui.view_models.auth

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject
import com.google.firebase.auth.GoogleAuthProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.noxinfinity.pdate.data.data_source.local.SharedPreferencesManager

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val sharedPreferences: SharedPreferencesManager
) : ViewModel() {

    private val _authState = MutableStateFlow(AuthState())
    val authState: StateFlow<AuthState> = _authState

    init {
        checkLoginState()
    }

    private fun checkLoginState() {
        val token = sharedPreferences.getToken()
        if(token != null) {
            Log.d("TOKEN_LOGGED", token)
            _authState.value = _authState.value.copy(isLoggedIn = true)
        }
    }

    fun firebaseAuthWithGoogle(idToken: String) {
        viewModelScope.launch {
            val credential = GoogleAuthProvider.getCredential(idToken, null)
            auth.signInWithCredential(credential).addOnCompleteListener{
                if(it.isSuccessful) {
                    val user = auth.currentUser
                    Log.d("ID TOKEN", idToken)
                    _authState.value = _authState.value.copy(isSuccess = true).copy(isLoggedIn = true)
                    sharedPreferences.saveToken(idToken)

                } else {
                    Log.d("GOOGLE", "firebaseAuthWithGoogle: ${it.exception}")
                }
            }
        }
    }

    fun signOut() {
        auth.signOut()
        sharedPreferences.clearToken()
        _authState.value = _authState.value.copy(isLoggedIn = false)
    }

}