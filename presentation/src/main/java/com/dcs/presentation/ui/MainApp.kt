package com.dcs.presentation.ui

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
import com.dcs.presentation.ui.home.HomeRoute
import com.dcs.presentation.ui.Screen.Main
import com.dcs.presentation.ui.main.MainBottomNavigation
import com.dcs.presentation.ui.people.PeopleRoute
import com.dcs.presentation.ui.setting.SettingRoute
import com.dcs.presentation.ui.trend.TrendRoute
import com.dcs.presentation.ui.signin.SignInRoute

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
            HomeRoute()
        }

        composable(route = Main.Trend.route) {
            TrendRoute()
        }

        composable(route = Main.People.route) {
            PeopleRoute()
        }

        composable(route = Main.Setting.route) {
            SettingRoute(
                navController = navController,
            )
        }

        // TODO: MainNavHost 랑 분리 필요, MainApp 함수 리팩토링 필요함
        composable(route = Screen.SignIn.route) {
            SignInRoute(navController = navController)
        }
    }
}
