package com.noxinfinity.pdate.ui.screens.nearby

import android.Manifest
import android.content.pm.PackageManager
import com.mapbox.geojson.Point
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationServices
import com.noxinfinity.pdate.ui.MainActivity
import com.noxinfinity.pdate.ui.common.components.MapBoxMap

@Composable
fun NearbyScreen() {
    val context = LocalContext.current
    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }

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
        CircularProgressIndicator(modifier = Modifier.fillMaxSize())
    } else {
        MapBoxMap(
            point = currentLocationPoint
        )
    }
}
