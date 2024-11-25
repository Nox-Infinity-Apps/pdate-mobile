package com.noxinfinity.pdate.ui.view_models.auth

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import com.google.firebase.auth.GoogleAuthProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val auth: FirebaseAuth,
) : ViewModel() {

    private val _authState = MutableStateFlow(AuthState())
    val authState: StateFlow<AuthState> = _authState

    fun firebaseAuthWithGoogle(idToken: String) {
        viewModelScope.launch {
            val credential = GoogleAuthProvider.getCredential(idToken, null)
            Log.d("GOOGLE", "firebaseAuthWithGoogle: ${credential}")
            auth.signInWithCredential(credential).addOnCompleteListener{
                if(it.isSuccessful) {
                    val user = auth.currentUser
                    Log.d("GOOGLE", "firebaseAuthWithGoogle: ${user}")
                    _authState.value = _authState.value.copy(isSuccess = true)
                } else {
                    Log.d("GOOGLE", "firebaseAuthWithGoogle: ${it.exception}")
                }
            }
        }
    }

}