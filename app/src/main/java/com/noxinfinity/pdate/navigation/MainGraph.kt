package com.noxinfinity.pdate.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.noxinfinity.pdate.ui.screens.chat.ChatScreen
import com.noxinfinity.pdate.ui.screens.common.PlaceHolder
import com.noxinfinity.pdate.ui.screens.home.HomeScreen
import com.noxinfinity.pdate.ui.screens.profile.ProfileScreen


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
