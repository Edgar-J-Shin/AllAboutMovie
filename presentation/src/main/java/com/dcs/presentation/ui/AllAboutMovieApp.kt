package com.dcs.presentation.ui

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dcs.presentation.ui.Screen.Main
import com.dcs.presentation.ui.Screen.SearchResult
import com.dcs.presentation.ui.Screen.SignIn
import com.dcs.presentation.ui.main.MainRoute
import com.dcs.presentation.ui.moviedetail.MovieDetailRoute
import com.dcs.presentation.ui.persondetail.PersonDetailRoute
import com.dcs.presentation.ui.searchresult.SearchResultRoute
import com.dcs.presentation.ui.signin.SignInRoute
import com.dcs.presentation.ui.tvshowdetail.TvShowDetailRoute

@Composable
fun AllAboutMovieApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = Main.route,
        enterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(500)
            )
        },
        exitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(500)
            )
        },
        popEnterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(500)
            )
        },
        popExitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(500)
            )
        },
        modifier = modifier
    ) {
        composable(route = Main.route) {
            MainRoute(navController = navController)
        }

        composable(
            route = SignIn.route,
            arguments = SignIn.navArguments
        ) {
            SignInRoute(navController = navController)
        }

        composable(
            route = SearchResult.route,
            arguments = SearchResult.navArguments
        ) {
            SearchResultRoute(
                navigateUp = { navController.popBackStack() },
                navigateToDetails = { id ->
                    navController.navigate(Screen.MovieDetail.createRoute(id))
                },
            )
        }

        composable(
            route = Screen.PersonDetail.route,
            arguments = Screen.PersonDetail.navArguments
        ) {
            PersonDetailRoute(
                navigateUp = { navController.popBackStack() },
            )
        }

        composable(
            route = Screen.MovieDetail.route,
            arguments = Screen.MovieDetail.navArguments
        ) {
            MovieDetailRoute(
                navigateUp = { navController.popBackStack() }
            )
        }

        composable(
            route = Screen.TvShowDetail.route,
            arguments = Screen.TvShowDetail.navArguments
        ) {
            TvShowDetailRoute(
                navigateUp = { navController.popBackStack() }
            )
        }
    }
}

