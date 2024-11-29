package com.noxinfinity.pdate.ui

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import android.Manifest
import com.noxinfinity.pdate.navigation.RootGraph
import com.noxinfinity.pdate.ui.screens.theme.DatingApplicationTheme
import com.noxinfinity.pdate.ui.view_models.auth.AuthViewModel
import com.noxinfinity.pdate.ui.view_models.chat.ChatViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.state.plugin.config.StatePluginConfig
import io.getstream.chat.android.state.plugin.factory.StreamStatePluginFactory
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var googleSignInClient: GoogleSignInClient

    private val authViewModel: AuthViewModel by viewModels()
    private val chatViewModel: ChatViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()



        val statePluginFactory = StreamStatePluginFactory(
            config = StatePluginConfig(
                backgroundSyncEnabled = true,
                userPresence = true
            ),
            appContext = this
        )
        ChatClient.Builder("625kk64h8kah", this)
            .withPlugins(statePluginFactory)
            .build()



        val signInLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                val account = task.getResult(Exception::class.java)
                account?.idToken?.let {
                    Log.d("TOKEN", it)
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
