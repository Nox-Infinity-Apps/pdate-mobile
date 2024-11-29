package com.noxinfinity.pdate.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.noxinfinity.pdate.R
import com.noxinfinity.pdate.ui.screens.home.components.HomeHeader
import com.noxinfinity.pdate.ui.screens.home.components.HomeSwipeBox
import com.noxinfinity.pdate.ui.view_models.home.HomeViewModel
import com.noxinfinity.pdate.ui.view_models.main.MainState
import com.noxinfinity.pdate.ui.view_models.main.MainViewModel
import com.noxinfinity.pdate.utils.heightPadding


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

    val isShowDialog = viewState.isDialogShow

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

        if (isShowDialog) {
            AlertDialog(
                onDismissRequest = { homeViewModel.onDismissDialog() }, // Close dialog on outside click
                title = {
                    Column(
                        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.match),
                            contentDescription = null,
                        )
                        8.heightPadding()
                        Text(
                            "Match thành công với người dùng ${viewState.dialogData.name}",
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        )
                    }
                },
                text = { Text("Bạn có muốn bắt đầu trò chuyện ngay không?", fontWeight = FontWeight.SemiBold, textAlign = TextAlign.Center) },
                confirmButton = {
                    Button(
                        onClick = {
                            homeViewModel.onDismissDialog()
                            ///TODO: Route qua man chat
                        },
                        colors = ButtonColors(
                            containerColor = Color(0xffff4459),
                            contentColor = Color.White,
                            disabledContainerColor = Color.Gray,
                            disabledContentColor = Color.White
                        ),
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .fillMaxWidth()
                    ) {
                        Text("Bắt đầu ngay", fontWeight = FontWeight.Bold)
                    }
                },
                dismissButton = {
                    Button(
                        onClick = { homeViewModel.onDismissDialog() },
                        colors = ButtonColors(
                            containerColor = Color.White,
                            contentColor = Color.Black,
                            disabledContainerColor = Color.Gray,
                            disabledContentColor = Color.White
                        ),
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .fillMaxWidth()

                    ) {
                        Text("Để sau", color = Color.Black, fontWeight = FontWeight.Bold)
                    }
                },
            )
        }

    }

}

