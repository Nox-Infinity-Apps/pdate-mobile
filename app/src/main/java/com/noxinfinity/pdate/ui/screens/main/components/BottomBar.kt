package com.noxinfinity.pdate.ui.screens.main.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.composables.icons.lucide.House
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.MessageCircleHeart
import com.composables.icons.lucide.Navigation
import com.composables.icons.lucide.UserRound
import com.noxinfinity.pdate.navigation.Graph

@Composable
fun BottomBar(modifier: Modifier = Modifier, navController: NavController) {

    val items = listOf(
        BottomNavItem("Trang chủ", Lucide.House, Graph.HOME),
        BottomNavItem("Gần đây", Lucide.Navigation, Graph.NEARBY),
        BottomNavItem("Trò chuyện", Lucide.MessageCircleHeart, Graph.CHAT),
        BottomNavItem("Cá nhân", Lucide.UserRound, Graph.PROFILE)
    )

    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    NavigationBar(
        containerColor = Color.White,
        modifier  = Modifier.clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
            .shadow(4.dp, shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)) // Add shadow to the top

    ) {
        items.forEach { item ->
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = item.title, modifier = Modifier.size(28.dp).padding(0.dp) ) },
                label = {},
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = false
                    }
                },
                alwaysShowLabel=false,
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color(0xFFFF2929),
                    unselectedIconColor = Color(0xffc2c4c8),
                    selectedTextColor = Color(0xFFFF2929),
                    unselectedTextColor = Color(0xffc2c4c8),
                    indicatorColor= Color.Transparent,
                ) ,

            )
        }
    }
}

data class BottomNavItem(val title: String, val icon: ImageVector, val route: String)