package com.noxinfinity.pdate

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.noxinfinity.pdate.navigation.RootGraph
import com.noxinfinity.pdate.ui.screens.theme.DatingApplicationTheme
import com.noxinfinity.pdate.ui.view_models.auth.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var googleSignInClient: GoogleSignInClient

    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val signInLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                val account = task.getResult(Exception::class.java)

//                Log.d("GOOGLE ACCOUNT", account.)

                account?.idToken?.let {
                    authViewModel.firebaseAuthWithGoogle(it)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        setContent {
            DatingApplicationTheme {
                RootGraph(authViewModel) {
                    signInLauncher.launch(googleSignInClient.signInIntent)
                }
            }
        }
    }


}
