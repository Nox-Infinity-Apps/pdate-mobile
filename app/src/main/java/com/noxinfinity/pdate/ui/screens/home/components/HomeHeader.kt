package com.noxinfinity.pdate.ui.screens.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.noxinfinity.pdate.R
import com.noxinfinity.pdate.navigation.Graph
import com.noxinfinity.pdate.ui.view_models.auth.AuthViewModel
import com.noxinfinity.pdate.utils.widthPadding

/**
 * Ý là mình không DI navcontroller được à đcm
 */
@Composable
fun HomeHeader(
    rootNavController: NavController,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val viewModel: AuthViewModel = hiltViewModel()


    val onSignOut = {
        viewModel.signOut{
            rootNavController.navigate(Graph.ONBOARDING) {
                popUpTo(rootNavController.graph.startDestinationId) {
                    inclusive = true
                }
            }
        }
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth().padding(12.dp)
    ){
        Image(
            painter = painterResource(id = R.drawable.default_avatar),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(40.dp).clip(CircleShape)
        )
        6.widthPadding()
        Column(
            modifier = Modifier
                .weight(1f),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Good Evening",
                fontSize = 14.sp,
                color = Color.Gray
            )
            Text(
                text = "John Doe",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
        }
        BadgedBox(
            badge = {
                Badge(
                    containerColor = Color.Red,
                    contentColor = Color.White
                ) {
                    Text("2")
                }
            },
            Modifier.clickable(onClick = onSignOut)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_notification),
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}
