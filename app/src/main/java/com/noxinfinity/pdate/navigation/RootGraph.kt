package com.noxinfinity.pdate.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.noxinfinity.pdate.ui.screens.auth.LoginScreen
import com.noxinfinity.pdate.ui.screens.main.MainScreen
import com.noxinfinity.pdate.ui.view_models.auth.AuthViewModel

@Composable
fun RootGraph(viewModel: AuthViewModel, onLogin: () -> Unit) {
    val navController = rememberNavController()
    NavHost(
        route = Graph.ROOT,
        navController = navController,
        startDestination = Graph.MAIN,
    ) {
        composable(Graph.LOGIN) {
            LoginScreen(
                onLogin,
                toMainScreen = {
                    navController.navigate(Graph.MAIN) {
                        popUpTo(navController.graph.startDestinationId) {
                            inclusive = true
                        }
                    }
                },
                viewModel
            )
        }
        composable(Graph.MAIN) {
            MainScreen(
                rootNavController = navController,
            )
        }
    }

}
