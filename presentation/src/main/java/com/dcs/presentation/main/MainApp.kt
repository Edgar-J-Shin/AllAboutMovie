package com.dcs.presentation.main

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
import com.dcs.presentation.home.HomeScreen
import com.dcs.presentation.main.Screen.Main
import com.dcs.presentation.people.PeopleScreen
import com.dcs.presentation.trend.SearchScreen
import com.dcs.presentation.setting.SettingScreen

@Composable
fun MainApp(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier,
) {

    Scaffold(
        modifier = modifier.fillMaxSize(),
        bottomBar = {
            MainBottomNavigation(navController = navController)
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            MainNavHost(
                navController = navController, startDestination = Main.Home.route
            )
        }
    }
}

@Composable
private fun MainNavHost(
    navController: NavHostController,
    startDestination: String,
    modifier: Modifier = Modifier,
) {

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable(route = Main.Home.route) {
            HomeScreen()
        }

        composable(route = Main.Search.route) {
            SearchScreen()
        }

        composable(route = Main.People.route) {
            PeopleScreen()
        }

        composable(route = Main.Setting.route) {
            SettingScreen()
        }
    }
}
