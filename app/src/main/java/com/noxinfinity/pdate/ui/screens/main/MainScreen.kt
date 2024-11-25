package com.noxinfinity.pdate.ui.screens.main

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat.finishAffinity
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.noxinfinity.pdate.MainActivity
import com.noxinfinity.pdate.navigation.MainGraph
import com.noxinfinity.pdate.ui.screens.main.components.BottomBar

@Composable
fun MainScreen(
    rootNavController: NavHostController,
) {
    val navController = rememberNavController()
    val context = LocalContext.current

    fun checkPermission() : Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION,
        ) == PackageManager.PERMISSION_GRANTED
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if(!isGranted) {
            (context as MainActivity).finish()
        }
    }

    LaunchedEffect(Unit) {
        if(!checkPermission()) {
            permissionLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
        }
    }

    Scaffold(
        bottomBar = {
            BottomBar(navController = navController)
        }
    ) { innerPadding ->
        MainGraph(
            navController = navController,
            modifier = Modifier.padding(innerPadding),
        )
    }
}