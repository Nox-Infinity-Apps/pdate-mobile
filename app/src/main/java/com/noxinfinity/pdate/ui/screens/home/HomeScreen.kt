package com.noxinfinity.pdate.ui.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.noxinfinity.pdate.ui.screens.home.components.HomeHeader
import com.noxinfinity.pdate.ui.screens.home.components.HomeSwipeBox
import com.noxinfinity.pdate.ui.view_models.home.HomeViewModel


@Composable
fun HomeScreen( modifier: Modifier = Modifier) {
    val homeViewModel: HomeViewModel = hiltViewModel()
    val viewState = homeViewModel.state.collectAsState().value
    Column {
        HomeHeader()
        HomeSwipeBox(
            state = viewState,
            onTriggerEvent = homeViewModel::onTriggerEvent
        )
    }
}

