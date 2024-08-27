package com.dcs.presentation.ui.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dcs.presentation.ui.Screen
import com.dcs.presentation.ui.Screen.Main.MainTab
import com.dcs.presentation.ui.home.HomeRoute
import com.dcs.presentation.ui.people.PeopleRoute
import com.dcs.presentation.ui.setting.SettingRoute
import com.dcs.presentation.ui.trend.TrendRoute
import kotlinx.coroutines.launch

@Composable
fun MainRoute(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    mainNavController: NavHostController = rememberNavController(),
    searchActive: MutableState<Boolean> = remember { mutableStateOf(false) },
) {
    val snackBarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        bottomBar = {
            MainBottomNavigation(
                navController = mainNavController,
                searchActive = searchActive.value
            )
        },
        snackbarHost = {
            SnackbarHost(
                hostState = snackBarHostState,
            )
        },
        modifier = modifier.fillMaxSize(),
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            MainNavHost(
                mainNavController = mainNavController,
                appNavHostController = navController,
                startDestination = MainTab.Home.route,
                searchActive = searchActive.value,
                onSearchActiveChange = { active ->
                    searchActive.value = active
                },
                showSnackBar = { message, duration ->
                    scope.launch {
                        snackBarHostState.showSnackbar(
                            message = message,
                            duration = duration
                        )
                    }
                }
            )
        }
    }
}

@Composable
private fun MainNavHost(
    mainNavController: NavHostController,
    appNavHostController: NavHostController,
    startDestination: String,
    searchActive: Boolean,
    onSearchActiveChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    showSnackBar: (String, SnackbarDuration) -> Unit = { _, _ -> },
) {

    NavHost(
        modifier = modifier,
        navController = mainNavController,
        startDestination = startDestination
    ) {
        composable(route = MainTab.Home.route) {
            HomeRoute(
                navigateToSearchResult = { keyword ->
                    appNavHostController.navigate(Screen.SearchResult.createRoute(keyword))
                },
                searchActive = searchActive,
                onSearchActiveChange = onSearchActiveChange,
                showSnackBar = showSnackBar
            )
        }

        composable(route = MainTab.Trend.route) {
            TrendRoute(
                navigateToDetails = { id ->
                    appNavHostController.navigate(Screen.MovieDetail.createRoute(id))
                },
                showSnackBar = showSnackBar
            )
        }

        composable(route = MainTab.People.route) {
            PeopleRoute(
                navigateToDetail = { personId ->
                    appNavHostController.navigate(Screen.PersonDetail.createRoute(personId))
                }
            )
        }

        composable(route = MainTab.Setting.route) {
            SettingRoute(
                navigateToSignIn = { requestToken ->
                    appNavHostController.navigate(Screen.SignIn.createRoute(requestToken))
                },
                showSnackBar = showSnackBar
            )
        }
    }
}
