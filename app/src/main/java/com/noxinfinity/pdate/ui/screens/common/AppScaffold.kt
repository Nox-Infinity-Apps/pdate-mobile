package com.noxinfinity.pdate.ui.screens.common

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color


@Composable
fun AppScaffold(
    modifier: Modifier = Modifier,
    snackBarHost: @Composable () -> Unit = {
        val snackBarHostState = remember { SnackbarHostState() }

        AppSnackBar(
            snackBarHostState = snackBarHostState,
            snackBarEnum = SnackBarEnum.ERROR
        )
    },
    topBar: @Composable (() -> Unit) = {},
    bottomBar: @Composable () -> Unit = {},
    floatingActionButton: @Composable (() -> Unit) = {},
    backgroundColor: Color = MaterialTheme.colorScheme.background,
    contentColor: Color = contentColorFor(backgroundColor),
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        modifier = modifier,
        snackbarHost = snackBarHost,
        topBar = topBar,
        content = content,
        bottomBar = bottomBar,
        contentColor = contentColor,
        floatingActionButton = floatingActionButton,
        floatingActionButtonPosition = FabPosition.Center,
        containerColor = backgroundColor
    )
}