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


@Composable
fun HomeScreen(
    rootNavController: NavController,
    navController: NavController,
    modifier: Modifier = Modifier) {
    val homeViewModel: HomeViewModel = hiltViewModel()
    val viewState = homeViewModel.state.collectAsState().value
    Column {
        HomeHeader(
            rootNavController = rootNavController,
            navController = navController,
            modifier = modifier
        )
        HomeSwipeBox(
            state = viewState,
            onTriggerEvent = homeViewModel::onTriggerEvent
        )
    }
}

