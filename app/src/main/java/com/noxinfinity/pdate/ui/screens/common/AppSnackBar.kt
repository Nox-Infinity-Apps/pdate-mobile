package com.noxinfinity.pdate.ui.screens.common

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.integerResource
import androidx.compose.ui.tooling.preview.Preview
import com.noxinfinity.pdate.R
import com.noxinfinity.pdate.ui.screens.theme.DatingApplicationTheme

@Composable
fun AppSnackBar(
    snackBarHostState: SnackbarHostState,
    snackBarEnum: SnackBarEnum
) {
    SnackbarHost(snackBarHostState) { data ->
        Snackbar(
            containerColor = Color(integerResource(id = snackBarEnum.backgroundColor)),
            snackbarData = data,
            shape = MaterialTheme.shapes.medium
        )
    }
}

sealed class SnackBarEnum(val backgroundColor: Int) {
    //TODO("Add your own SnackBarEnum")
    data object SUCCESS : SnackBarEnum(R.color.black)
    data object ERROR : SnackBarEnum(R.color.black)
}

@Preview
@Composable
private fun BodyPreview() {
    val snackBarHostState = SnackbarHostState(

    )
    DatingApplicationTheme {
        AppSnackBar(
            snackBarHostState,
            SnackBarEnum.SUCCESS
        )
    }
}