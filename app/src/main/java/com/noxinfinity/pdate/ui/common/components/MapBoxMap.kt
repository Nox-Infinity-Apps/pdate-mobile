package com.noxinfinity.pdate.ui.common.components

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmap
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.mapbox.geojson.Point
import com.mapbox.maps.Style
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import com.mapbox.maps.extension.compose.annotation.ViewAnnotation
import com.mapbox.maps.extension.compose.annotation.generated.CircleAnnotation
import com.mapbox.maps.extension.compose.style.BooleanValue
import com.mapbox.maps.extension.compose.style.lights.LightsState
import com.mapbox.maps.extension.compose.style.standard.MapboxStandardStyle
import com.mapbox.maps.plugin.annotation.generated.PointAnnotation
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationManager
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions
import com.mapbox.maps.viewannotation.geometry
import com.mapbox.maps.viewannotation.viewAnnotationOptions
import com.noxinfinity.pdate.R
import com.noxinfinity.pdate.SuggestUsersNearByQuery
import com.noxinfinity.pdate.navigation.Graph
import com.noxinfinity.pdate.ui.screens.nearby.components.AvatarView
import com.noxinfinity.pdate.ui.view_models.main.MainState
import com.noxinfinity.pdate.ui.view_models.main.MainViewModel
import com.noxinfinity.pdate.ui.view_models.nearby.NearByViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition", "MissingPermission")
@OptIn(DelicateCoroutinesApi::class, ExperimentalMaterial3Api::class)
@Composable
fun MapBoxMap(
	navController: NavController,
	mainViewModel: MainViewModel,
	nearByViewModel : NearByViewModel,
	modifier: Modifier = Modifier,
	point: Point?,
) {
	val context = LocalContext.current
	val mainState = mainViewModel.uiState.collectAsState().value
	val nearByViewState = nearByViewModel.state.collectAsState().value
	val coroutineScope = rememberCoroutineScope()

	val sheetState = rememberModalBottomSheetState(
		skipPartiallyExpanded = true,
	)

	var targetUser by remember { mutableStateOf< SuggestUsersNearByQuery. SuggestedUsersNearBy?>(null) }



	val user = (mainState as MainState.Success).user

	val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
	val marker = remember(context) {
		val originalMarker = context.getDrawable(R.drawable.ic_heart)!!.toBitmap()
		Bitmap.createScaledBitmap(originalMarker, 64, 64, false)
	}


	val systemUiController = rememberSystemUiController()
	val useDarkIcons = !isSystemInDarkTheme()

	DisposableEffect(systemUiController, useDarkIcons) {
		systemUiController.setSystemBarsColor(
			color = Color.Transparent,
			darkIcons = useDarkIcons,
			isNavigationBarContrastEnforced = false
		)

		onDispose {}
	}


	var userLocation: Location? by remember { mutableStateOf(null) }

	var pointAnnotationManager: PointAnnotationManager? by remember {
		mutableStateOf(null)
	}

	GlobalScope.launch(Dispatchers.Main) {
		fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
			location?.let {
				userLocation = it
			}
		}
	}


	userLocation?.let { location ->
		val userPoint = Point.fromLngLat(location.longitude, location.latitude)

		val mapViewportState = rememberMapViewportState {
			setCameraOptions {
				zoom(14.0)
				center(userPoint)
				pitch(0.0)
				bearing(0.0)
			}
		}


		MapboxMap(
			modifier = Modifier.fillMaxSize(),
			mapViewportState = mapViewportState,
			style = {
				MapboxStandardStyle {
					showPlaceLabels = BooleanValue(false)

				}
			},
			scaleBar = {},
			logo = {},
			compass = {}
		){
			if(sheetState.isVisible) {
				ProfileSheet(sheetState, targetUser, coroutineScope)
			}

			AvatarView(
				navController = navController,
				userPoint = userPoint,
				avatar = user.avatar,
				userId = user.fcm_id,
				isMe = true
			)
			nearByViewState.nearByUsersList.forEach { user ->
				if(user?.lat == null || user.lng == null) return@forEach else {
					if (user.lat in -90.0..90.0 && user.lng in -180.0..180.0) {
						AvatarView(
							navController = navController,
							userPoint = Point.fromLngLat(user.lng, user.lat),
							avatar = user.avatarUrl,
							userId = user.fcmId,
							isMe = false,
							onClick = {
								targetUser = user
								coroutineScope.launch {
									sheetState.show()
								}
							}
						)
					}
				}
			}
		}

		pointAnnotationManager?.let { manager ->
			val pointAnnotation = PointAnnotationOptions()
				.withPoint(userPoint)
				.withIconImage(marker)

			manager.create(pointAnnotation)
		}
	} ?: run {
		//TODO: Handle user location not available

	}

}
