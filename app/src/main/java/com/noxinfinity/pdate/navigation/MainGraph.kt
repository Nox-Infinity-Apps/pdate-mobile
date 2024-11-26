package com.noxinfinity.pdate.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.mapbox.geojson.Point
import com.noxinfinity.pdate.ui.common.components.MapBoxMap
import com.noxinfinity.pdate.ui.screens.chat.ChatScreen
import com.noxinfinity.pdate.ui.screens.common.PlaceHolder
import com.noxinfinity.pdate.ui.screens.home.HomeScreen
import com.noxinfinity.pdate.ui.screens.profile.ProfileScreen
import io.getstream.chat.android.compose.ui.theme.ChatTheme


@Composable
fun MainGraph(
    rootNavController: NavHostController,
    navController: NavHostController, modifier: Modifier = Modifier) {
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
            )
        }
        composable(Graph.NEARBY) {
            MapBoxMap(
                modifier = Modifier,
                point = Point.fromLngLat(
                    21.0122,
                    52.2297
                )
            )
        }
        composable(Graph.CHAT) {
            ChatTheme {
                ChatScreen()
            }
        }
        composable(Graph.LOVE) {
            PlaceHolder()
        }
        composable(Graph.PROFILE) {
            ProfileScreen()
        }
    }
}
