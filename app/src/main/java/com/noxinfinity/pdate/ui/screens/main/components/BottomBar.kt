package com.noxinfinity.pdate.ui.screens.main.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.noxinfinity.pdate.navigation.Graph
import compose.icons.FeatherIcons
import compose.icons.FontAwesomeIcons
import compose.icons.SimpleIcons
import compose.icons.feathericons.MoreHorizontal
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.Heart
import compose.icons.fontawesomeicons.solid.Home
import compose.icons.fontawesomeicons.solid.LocationArrow
import compose.icons.simpleicons.Livechat

@Composable
fun BottomBar(modifier: Modifier = Modifier, navController: NavController) {

    val items = listOf(
        BottomNavItem("Home", FontAwesomeIcons.Solid.Home, Graph.HOME),
        BottomNavItem("Nearby", FontAwesomeIcons.Solid.LocationArrow, Graph.NEARBY),
        BottomNavItem("Chat", SimpleIcons.Livechat, Graph.CHAT),
        BottomNavItem("Love", FontAwesomeIcons.Solid.Heart, Graph.LOVE),
        BottomNavItem("More", FeatherIcons.MoreHorizontal, Graph.PROFILE)
    )

    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    NavigationBar(
        containerColor = Color.White
    ) {
        items.forEach { item ->
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = item.title, modifier = Modifier.size(24.dp)) },
                label = { Text(text = item.title) },
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.Red,
                    unselectedIconColor = Color.Gray,
                    selectedTextColor = Color.Red,
                    unselectedTextColor = Color.Gray
                )
            )
        }
    }
}

data class BottomNavItem(val title: String, val icon: ImageVector, val route: String)