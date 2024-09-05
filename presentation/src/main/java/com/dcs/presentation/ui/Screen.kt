package com.dcs.presentation.ui

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.dcs.presentation.R
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

sealed class Screen(
    val route: String,
    val navArguments: ImmutableList<NamedNavArgument> = persistentListOf(),
) {

    data object Main : Screen(route = ROUTE_MAIN) {

        sealed class MainTab(
            route: String,
            @StringRes val titleResId: Int,
            @DrawableRes val iconResId: Int,
        ) : Screen(route, persistentListOf()) {

            data object Home : MainTab(
                route = ROUTE_HOME,
                titleResId = R.string.home,
                iconResId = R.drawable.ic_home
            )

            data object Trend : MainTab(
                route = ROUTE_TREND,
                titleResId = R.string.trend,
                iconResId = R.drawable.ic_trend
            )

            data object People : MainTab(
                route = ROUTE_PEOPLE,
                titleResId = R.string.people,
                iconResId = R.drawable.ic_people
            )

            data object Setting : MainTab(
                route = ROUTE_SETTING,
                titleResId = R.string.setting,
                iconResId = R.drawable.ic_setting
            )
        }
    }

    data object MovieDetail : Screen(
        route = "$ROUTE_MOVIE_DETAIL/{$MOVIE_ID_SAVED_STATE_KEY}",
        navArguments = persistentListOf(navArgument(MOVIE_ID_SAVED_STATE_KEY) {
            type = NavType.IntType
        })
    ) {
        fun createRoute(movieId: Int) = "$ROUTE_MOVIE_DETAIL/${movieId}"

    }

    data object TvShowDetail : Screen(
        route = "$ROUTE_TV_SHOW_DETAIL/{$TV_SHOW_ID_SAVED_STATE_KEY}",
        navArguments = persistentListOf(navArgument(TV_SHOW_ID_SAVED_STATE_KEY) {
            type = NavType.IntType
        })
    ) {
        fun createRoute(tvShowId: Int) = "$ROUTE_TV_SHOW_DETAIL/${tvShowId}"

    }

    data object SearchResult : Screen(
        route = "$ROUTE_SEARCH_RESULT/{$SEARCH_RESULT_KEYWORD}",
        navArguments = persistentListOf(
            navArgument(SEARCH_RESULT_KEYWORD) {
                type = NavType.StringType
            })
    ) {
        fun createRoute(keyword: String) = "$ROUTE_SEARCH_RESULT/${keyword}"
    }

    data object SignIn : Screen(
        route = "$ROUTE_SIGN_IN/{$SIGN_IN_REQUEST_TOKEN_KEY}",
        navArguments = persistentListOf(
            navArgument(SIGN_IN_REQUEST_TOKEN_KEY) {
                type = NavType.StringType
            }
        )
    ) {
        fun createRoute(requestToken: String) = "$ROUTE_SIGN_IN/${requestToken}"
    }

    data object PersonDetail : Screen(
        route = "$ROUTE_PERSON_DETAIL/{$PERSON_DETAIL_ID_KEY}",
        navArguments = persistentListOf(navArgument(PERSON_DETAIL_ID_KEY) {
            type = NavType.LongType
        })
    ) {
        fun createRoute(personId: Int) = "$ROUTE_PERSON_DETAIL/${personId}"
    }

    companion object {
        const val ROUTE_MAIN = "main"
        const val ROUTE_HOME = "home"
        const val ROUTE_TREND = "trend"
        const val ROUTE_PEOPLE = "people"
        const val ROUTE_SETTING = "setting"
        const val ROUTE_MOVIE_DETAIL = "movieDetail"
        const val ROUTE_TV_SHOW_DETAIL = "tvShowDetail"
        const val ROUTE_SIGN_IN = "signIn"
        const val ROUTE_SEARCH_RESULT = "searchResult"
        const val ROUTE_PERSON_DETAIL = "personDetail"

        const val MOVIE_ID_SAVED_STATE_KEY = "MovieIdSavedStateKey"
        const val TV_SHOW_ID_SAVED_STATE_KEY = "TvShowIdSavedStateKey"
        const val SIGN_IN_REQUEST_TOKEN_KEY = "SignInRequestTokenKey"
        const val SEARCH_RESULT_KEYWORD = "SearchResultKeyword"
        const val PERSON_DETAIL_ID_KEY = "PersonDetailIdKey"
    }
}
