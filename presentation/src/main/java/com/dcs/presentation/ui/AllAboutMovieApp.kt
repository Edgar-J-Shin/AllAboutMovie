package com.dcs.presentation.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dcs.presentation.ui.Screen.Main
import com.dcs.presentation.ui.Screen.SignIn
import com.dcs.presentation.ui.main.MainRoute
import com.dcs.presentation.ui.signin.SignInRoute

@Composable
fun AllAboutMovieApp(
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = Main.route
    ) {
        composable(route = Main.route) {
            MainRoute(navController = navController)
        }

        composable(route = SignIn.route) {
            SignInRoute(navController = navController)
        }

    }
}
