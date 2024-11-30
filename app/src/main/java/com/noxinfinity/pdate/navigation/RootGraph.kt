package com.noxinfinity.pdate.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.noxinfinity.pdate.ui.screens.chat.ConversationScreen
import com.noxinfinity.pdate.ui.screens.edit_profile.EditProfileScreen
import com.noxinfinity.pdate.ui.screens.empty.EmptyScreen
import com.noxinfinity.pdate.ui.screens.main.MainScreen
import com.noxinfinity.pdate.ui.screens.onboading.OnboardingScreen
import com.noxinfinity.pdate.ui.view_models.auth.AuthViewModel

@Composable
fun RootGraph(
    viewModel: AuthViewModel,
    onLogin: () -> Unit
) {
    val navController = rememberNavController()
    val inMainNavController = rememberNavController()

    val isLoggedIn by viewModel.authState.collectAsState()

    LaunchedEffect(Unit) {
        snapshotFlow { isLoggedIn }
            .collect { authState ->
                if(!authState.isLoading) {
                    if (authState.isLoggedIn) {
                        navController.navigate(Graph.MAIN) {
                            popUpTo(navController.graph.startDestinationId) {
                                inclusive = true
                            }
                        }
                    } else {
                        navController.navigate(Graph.ONBOARDING) {
                            popUpTo(navController.graph.startDestinationId) {
                                inclusive = true
                            }
                        }
                    }
                }
            }
    }

    NavHost(
        route = Graph.ROOT,
        navController = navController,
        startDestination = Graph.EMPTY,
    ) {
        composable(Graph.EMPTY) {
            EmptyScreen(
                viewModel = viewModel,
            )
        }

        composable(Graph.ONBOARDING) {
            OnboardingScreen(
                viewModel = viewModel,
                onLogin = onLogin,
                rootNavController = navController,
            )
        }

        composable(Graph.MAIN) {
            MainScreen(
                rootNavController = navController,
                authViewModel = viewModel,
                navController = inMainNavController
            )
        }

        composable(Graph.MESSAGE) {
            ConversationScreen(it.arguments?.getString("id")!!,
                navController = navController
            )
        }

        composable("${Graph.EDIT_PROFILE}/{canPop}") { backStackEntry ->
            val canBack = backStackEntry.arguments?.getBoolean("canPop") ?: true
            EditProfileScreen(
                onBack =  {
                    navController.popBackStack()
                },
                canReturn = canBack,
                onSave = {
                    navController.navigate(Graph.MAIN) {
                        popUpTo(navController.graph.startDestinationId)
                    }
                }
            )
        }
    }
}
