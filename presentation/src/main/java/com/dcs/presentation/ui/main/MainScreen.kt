package com.dcs.presentation.ui.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dcs.presentation.ui.Screen.Main.MainTab
import com.dcs.presentation.ui.home.HomeRoute
import com.dcs.presentation.ui.people.PeopleRoute
import com.dcs.presentation.ui.setting.SettingRoute
import com.dcs.presentation.ui.trend.TrendRoute

@Composable
fun MainRoute(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    mainNavController: NavHostController = rememberNavController(),
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        bottomBar = {
            MainBottomNavigation(navController = mainNavController)
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            MainNavHost(
                mainNavController = mainNavController,
                appNavHostController = navController,
                startDestination = MainTab.Home.route
            )
        }
    }
}

@Composable
private fun MainNavHost(
    mainNavController: NavHostController,
    appNavHostController: NavHostController,
    startDestination: String,
    modifier: Modifier = Modifier,
) {

    NavHost(
        modifier = modifier,
        navController = mainNavController,
        startDestination = startDestination
    ) {
        composable(route = MainTab.Home.route) {
            HomeRoute(
                navController = appNavHostController,
            )
        }

        composable(route = MainTab.Trend.route) {
            TrendRoute()
        }

        composable(route = MainTab.People.route) {
            PeopleRoute()
        }

        composable(route = MainTab.Setting.route) {
            SettingRoute(
                navController = appNavHostController,
            )
        }
    }
}
