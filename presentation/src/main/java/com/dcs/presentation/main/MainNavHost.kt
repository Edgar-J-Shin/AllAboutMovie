package com.dcs.presentation.main

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dcs.presentation.home.HomeScreen
import com.dcs.presentation.people.PeopleScreen
import com.dcs.presentation.search.SearchScreen
import com.dcs.presentation.setting.SettingScreen

@Composable
fun MainNavHost(
    navController: NavHostController = rememberNavController(),
) {

    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(route = Screen.Home.route) {
            HomeScreen()
        }

        composable(route = Screen.Search.route) {
            SearchScreen()
        }

        composable(route = Screen.People.route) {
            PeopleScreen()
        }

        composable(route = Screen.Setting.route) {
            SettingScreen()
        }
    }
}
