package com.noxinfinity.pdate.ui.screens.main

import android.Manifest
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.location.LocationServices
import com.google.firebase.messaging.FirebaseMessaging
import com.noxinfinity.pdate.navigation.Graph
import com.noxinfinity.pdate.navigation.MainGraph
import com.noxinfinity.pdate.ui.common.components.AppIndicator
import com.noxinfinity.pdate.ui.screens.main.components.BottomBar
import com.noxinfinity.pdate.ui.view_models.auth.AuthViewModel
import com.noxinfinity.pdate.ui.view_models.main.MainState
import com.noxinfinity.pdate.ui.view_models.main.MainViewModel
import com.noxinfinity.pdate.utils.helper.LocationHelper
import com.noxinfinity.pdate.utils.helper.PermissionHelper

@Composable
fun MainScreen(
    rootNavController: NavHostController,
    navController: NavHostController,
    authViewModel: AuthViewModel,
) {

    val context = LocalContext.current

    val viewModel: MainViewModel = hiltViewModel()

    val notificationLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) {}


    LaunchedEffect(Unit) {

        if (!PermissionHelper.checkNotificationPermission(context)) {
            notificationLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }

        var token = ""

        FirebaseMessaging.getInstance().token
            .addOnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w("FCM", "Fetching FCM registration token failed", task.exception)
                }
                token = task.result

            }

        if(PermissionHelper.checkLocationPermission(context)) {
            LocationServices.getFusedLocationProviderClient(context).lastLocation.addOnCompleteListener{
                val location = it.result
                LocationHelper.lat = location?.latitude ?: 0.0
                LocationHelper.lng = location?.longitude ?: 0.0

                viewModel.updateFcmAndLocation(
                    fcmToken = token,
                    lat = location?.latitude.toString(),
                    lng = location?.longitude.toString(),
                )
            }
        }

        viewModel.fetchUser()

    }

    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState) {
        if (uiState is MainState.Error && (uiState as MainState.Error).tokenTimeOut) {
            authViewModel.signOut {
            }
        }
        if(uiState is MainState.Success && (uiState as MainState.Success).isNew) {
            navController.navigate("${Graph.EDIT_PROFILE}/false") {
                popUpTo(navController.graph.startDestinationId) {
                    inclusive = true
                }
            }
        }
    }

    when (uiState) {
        is MainState.Error -> Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            Column {
                Text(
                    text = (uiState as MainState.Error).message
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = { viewModel.fetchUser() }
                ) {
                    Text(
                        text = "Try Again"
                    )
                }
            }
        }

        is MainState.Loading -> Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            AppIndicator()
        }

        is MainState.Success -> {
            Scaffold(
                bottomBar = {
                    BottomBar(navController = navController)
                }
            ) { innerPadding ->
                MainGraph(
                    rootNavController = rootNavController,
                    navController = navController,
                    modifier = Modifier.padding(innerPadding),
                    mainViewModel = viewModel,
                    onSignOut = {
                        authViewModel.signOut {

                        }
                    }
                )
            }
        }
    }

}