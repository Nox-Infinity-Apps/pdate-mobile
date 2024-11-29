package com.noxinfinity.pdate.ui.screens.nearby

import android.Manifest
import android.content.pm.PackageManager
import com.mapbox.geojson.Point
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.google.android.gms.location.LocationServices
import com.noxinfinity.pdate.ui.MainActivity
import com.noxinfinity.pdate.ui.common.components.MapBoxMap
import com.noxinfinity.pdate.ui.view_models.main.MainViewModel
import com.noxinfinity.pdate.ui.view_models.nearby.NearByViewModel

@Composable
fun NearbyScreen(
    viewModel: MainViewModel,
    navController: NavController
) {
    val context = LocalContext.current
    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }
    val nearByViewModel: NearByViewModel = hiltViewModel()

    var currentLocationPoint by remember { mutableStateOf<Point?>(null) }
    var isLoading by remember { mutableStateOf(true) }


    val locationPermissionGranted = ContextCompat.checkSelfPermission(
        context, Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED

    if (locationPermissionGranted) {
        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            location?.let {
                currentLocationPoint = Point.fromLngLat(it.longitude, it.latitude)
                isLoading = false
            }
        }
    } else {
        val requestPermissionLauncher = (context as MainActivity).activityResultRegistry
            .register("request_permission", ActivityResultContracts.RequestPermission()) { isGranted ->
                if (isGranted) {
                    fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                        location?.let {
                            currentLocationPoint = Point.fromLngLat(it.longitude, it.latitude)
                            isLoading = false
                        }
                    }
                } else {
                    isLoading = false
                }
            }

        if (!locationPermissionGranted) {
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    if (isLoading) {
        Box(modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator()
        }
    } else {
        Box(modifier = Modifier.fillMaxSize()) {
            MapBoxMap(
                navController,
                viewModel,
                point = currentLocationPoint,
                nearByViewModel = nearByViewModel
            )
            Text(
                text = "Khám phá",
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp,
                modifier = Modifier
                    .align(Alignment.TopStart).offset(16.dp, 16.dp)
            )
        }
    }
}
