package com.dcs.presentation.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.dcs.presentation.main.Screen.Main

@Composable
fun MainBottomNavigation(
    items: List<Main> =
        listOf(
            Main.Home,
            Main.Search,
            Main.People,
            Main.Setting,
        ),
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    AnimatedVisibility(visible = items.any { it.route == currentRoute }) {
        NavigationBar(
            modifier = modifier,
        ) {
            items.forEach { screen ->
                NavigationBarItem(
                    label = {
                        Text(text = stringResource(id = screen.titleResId))
                    },
                    selected = currentRoute == screen.route,
                    alwaysShowLabel = false,
                    icon = {
                        Icon(
                            painter = painterResource(id = screen.iconResId),
                            contentDescription = stringResource(id = screen.titleResId),
                        )
                    },
                    // TODO: Use the colors from the theme and add primary color
                    colors = NavigationBarItemDefaults.colors().copy(
                        selectedIconColor = Color(0xFF6200EE),
                        selectedTextColor = Color(0xFF6200EE),
                        unselectedIconColor = Color.Gray,
                        unselectedTextColor = Color.Gray,
                    ),
                    onClick = {
                        navController.navigate(screen.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                )
            }
        }
    }
}
