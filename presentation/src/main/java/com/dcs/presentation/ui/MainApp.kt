package com.dcs.presentation.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.dcs.presentation.ui.Screen.Main.MainTab
import com.dcs.presentation.ui.main.MainBottomNavigation

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
                navController = navController, startDestination = MainTab.Home.route
            )
        }
    }
}

//// TODO: MainNavHost 랑 분리 필요, MainApp 함수 리팩토링 필요함
//composable(route = Screen.SignIn.route) {
//    SignInRoute(navController = navController)
//}
