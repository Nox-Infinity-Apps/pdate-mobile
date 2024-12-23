package com.noxinfinity.pdate.ui.view_models.auth

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject
import com.google.firebase.auth.GoogleAuthProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.noxinfinity.pdate.data.data_source.local.SharedPreferencesManager
import com.noxinfinity.pdate.utils.helper.JWTHelper

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val sharedPreferences: SharedPreferencesManager
) : ViewModel() {

    private val _authState = MutableStateFlow(AuthState())
    val authState: StateFlow<AuthState> = _authState

    @Inject
    lateinit var googleSignInClient: GoogleSignInClient

    fun checkLoginState() {
        _authState.value = _authState.value.copy(isLoading = true)

        val token = sharedPreferences.getToken()
        Log.d("GOOGLE_TOKEN", token.toString())
        if (token != null && !JWTHelper.isJwtExpired(token)) {

            _authState.value = _authState.value.copy(isLoggedIn = true, isLoading = false)

        } else {
            _authState.value = _authState.value.copy(isLoggedIn = false, isLoading = false)
        }

    }

    fun resetAvatar() {
        _authState.value = _authState.value.copy(
            isReloadAvatar = true,
        )
        _authState.value = _authState.value.copy(
            isReloadAvatar = false,
        )
    }

    fun firebaseAuthWithGoogle(idToken: String) {
        viewModelScope.launch {
            val credential = GoogleAuthProvider.getCredential(idToken, null)
            auth.signInWithCredential(credential).addOnCompleteListener {
                if (it.isSuccessful) {
                    val user = auth.currentUser
                    _authState.value = _authState.value.copy(isSuccess = true, isLoggedIn = true)
                    user?.getIdToken(true)?.addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val accessToken = task.result?.token
                            if (accessToken != null) {
                                sharedPreferences.saveToken(accessToken)
                                Log.d("ACCESS_TOKEN", accessToken.toString())
                            }
                        } else {
                            Log.d("GOOGLE", "firebaseAuthWithGoogle: ${it.exception}")
                        }
                    }?.addOnFailureListener {
                        throw Exception("Failed to get access token")
                    }
                } else {
                    Log.d("GOOGLE", "firebaseAuthWithGoogle: ${it.exception}")
                }
            }
        }
    }

    public fun signOut(onSuccess: () -> Unit) {
        googleSignInClient.signOut().addOnCompleteListener {
            auth.signOut()
            sharedPreferences.clearToken()
            _authState.value = _authState.value.copy(
                isLoggedIn = false,
                isSuccess = false
            )
            Log.d("SIGN_OUT", "User signed out")
            onSuccess()
        }
    }

}