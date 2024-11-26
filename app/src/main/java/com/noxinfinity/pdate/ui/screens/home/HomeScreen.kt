package com.noxinfinity.pdate.ui.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.noxinfinity.pdate.ui.screens.home.components.HomeHeader
import com.noxinfinity.pdate.ui.screens.home.components.HomeSwipeBox
import com.noxinfinity.pdate.ui.view_models.home.HomeViewModel
import com.noxinfinity.pdate.ui.view_models.main.MainState
import com.noxinfinity.pdate.ui.view_models.main.MainViewModel


@Composable
fun HomeScreen(
    rootNavController: NavController,
    navController: NavController,
    mainViewModel: MainViewModel,
    modifier: Modifier = Modifier,
    ) {
    val homeViewModel: HomeViewModel = hiltViewModel()
    val viewState = homeViewModel.state.collectAsState().value
    val mainState = mainViewModel.uiState.collectAsState().value
    val user = (mainState as MainState.Success).user

    Column {
        HomeHeader(
            rootNavController = rootNavController,
            navController = navController,
            modifier = modifier,
            avatar = user.avatar!!,
            name = user.fullName,
        )
        HomeSwipeBox(
            state = viewState,
            onTriggerEvent = homeViewModel::onTriggerEvent
        )
    }
}

