package com.noxinfinity.pdate.ui.screens.auth

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.noxinfinity.pdate.ui.screens.chat.ChatScreen
import com.noxinfinity.pdate.ui.screens.theme.DatingApplicationTheme
import com.noxinfinity.pdate.ui.view_models.auth.AuthViewModel


@Composable
fun LoginScreen(onLogin: () -> Unit, toMainScreen: () -> Unit, viewModel: AuthViewModel, modifier: Modifier = Modifier,  ) {
    val authState by viewModel.authState.collectAsState()

    LaunchedEffect(authState.isSuccess) {

        if (authState.isSuccess) {
            Log.d("STATE","LoginScreen: ${authState.isSuccess}")
//            toMainScreen()
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier.fillMaxSize()
    ) {
        Button(
            onClick = onLogin
        ) {
            Text(text = "Sign In With cc")
        }
    }
}
