package com.noxinfinity.pdate.ui.screens.empty

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.noxinfinity.pdate.ui.MainActivity
import com.noxinfinity.pdate.ui.view_models.auth.AuthViewModel
import com.noxinfinity.pdate.utils.helper.PermissionHelper

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmptyScreen(viewModel: AuthViewModel, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    var showDialog by remember {
        mutableStateOf(false)
    }

    val locationLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (!isGranted) {
            showDialog = true
        }
    }

    LaunchedEffect(Unit) {
        if (!PermissionHelper.checkLocationPermission(context)) {
            locationLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
        }

        viewModel.checkLoginState()
    }


    if (showDialog) {
        BasicAlertDialog(
            onDismissRequest = {
                (context as MainActivity).finish()
            },
            modifier = Modifier.padding(16.dp)
        ) {
            Surface(
                shape = MaterialTheme.shapes.medium,
                color = MaterialTheme.colorScheme.surface,
                tonalElevation = 6.dp,
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Bạn phải cho phép truy cập vị trí để tiếp tục truy cập ứng dụng",
                    color = Color.Black,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }

}