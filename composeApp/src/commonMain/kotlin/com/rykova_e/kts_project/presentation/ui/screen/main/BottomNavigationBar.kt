package com.rykova_e.kts_project.presentation.ui.screen.main

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.rykova_e.kts_project.presentation.ui.navigation.Screen

@Stable
data class Item(
    var isSelected: Boolean = false,
    val description: String,
    val screen: Screen = Screen.MainScreen,
    val icon: ImageVector
)

val bottomBarDestinations = listOf(
    Item(
        screen = Screen.MainScreen,
        description = "Главная",
        icon = Icons.Default.Home
    ),
    Item(
        screen = Screen.ProfileScreen,
        description = "Профиль",
        icon = Icons.Default.Person
    )
)

@Composable
fun BottomNavigationBar(navController: NavController) {
    NavigationBar(
        modifier = Modifier
            .windowInsetsPadding(WindowInsets.navigationBars)
            .height(70.dp)
    ) {
        bottomBarDestinations.forEach { item ->
            NavigationBarItem(
                selected = when (item.screen) {
                    is Screen.MainScreen ->
                        navController.currentBackStackEntryAsState()
                            .value?.destination?.hasRoute(Screen.MainScreen::class) == true
                    is Screen.ProfileScreen ->
                        navController.currentBackStackEntryAsState()
                            .value?.destination?.hasRoute(Screen.ProfileScreen::class) == true
                    else -> false
                },
                onClick = {
                    navController.navigate(item.screen)
                },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = null
                    )
                }
            )
        }
    }
}

@Preview
@Composable
private fun BottomNavigationBarPreview() {
    BottomNavigationBar(rememberNavController())
}

