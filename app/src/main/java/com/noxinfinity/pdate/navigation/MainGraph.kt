package com.noxinfinity.pdate.navigation

//import com.mapbox.geojson.Point
//import com.noxinfinity.pdate.ui.common.components.MapBoxMap
import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Point
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.noxinfinity.pdate.ui.MainActivity
import com.noxinfinity.pdate.ui.screens.chat.ChatScreen
import com.noxinfinity.pdate.ui.screens.common.PlaceHolder
import com.noxinfinity.pdate.ui.screens.home.HomeScreen
import com.noxinfinity.pdate.ui.screens.profile.ProfileScreen
import com.noxinfinity.pdate.ui.view_models.main.MainViewModel

//import io.getstream.chat.android.compose.ui.theme.ChatTheme


@Composable
fun MainGraph(
    rootNavController: NavHostController,
    mainViewModel: MainViewModel,
    navController: NavHostController, modifier: Modifier = Modifier,
) {
    val context = LocalContext.current

    val fusedLocationClient = remember {
        com.google.android.gms.location.LocationServices.getFusedLocationProviderClient(context)
    }
    var currentLocationPoint by remember {
        mutableStateOf<Point?>(null)
    }

    if (ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED
    ) {
        ActivityCompat.requestPermissions(
            context as MainActivity,
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ),
            1
        )
    }
//    fusedLocationClient.lastLocation.addOnSuccessListener {
//        currentLocationPoint = Point.fromLngLat(it.longitude, it.latitude)
//    }


    NavHost(
        navController = navController,
        route = Graph.MAIN,
        startDestination = Graph.HOME,
        modifier = modifier
    ) {
        composable(Graph.HOME) {
            HomeScreen(
                rootNavController = rootNavController,
                navController = navController,
                mainViewModel = mainViewModel,
            )
        }
        composable(Graph.NEARBY) {
            PlaceHolder()
        }
        composable(Graph.CHAT) {
            ChatScreen()
        }
        composable(Graph.LOVE) {
            PlaceHolder()
        }
        composable(Graph.PROFILE) {
            ProfileScreen()
        }
    }
}
