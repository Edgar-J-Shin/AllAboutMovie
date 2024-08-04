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

    data object DetailItem : Screen(
        route = "$ROUTE_DETAIL/{$ID_SAVED_STATE_KEY}",
        navArguments = persistentListOf(navArgument(ID_SAVED_STATE_KEY) {
            type = NavType.StringType
        })
    ) {
        fun createRoute(itemId: String) = "$ROUTE_DETAIL/${itemId}"

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

    companion object {
        const val ROUTE_MAIN = "main"
        const val ROUTE_HOME = "home"
        const val ROUTE_TREND = "trend"
        const val ROUTE_PEOPLE = "people"
        const val ROUTE_SETTING = "setting"
        const val ROUTE_DETAIL = "detail"
        const val ROUTE_SIGN_IN = "signIn"

        const val ID_SAVED_STATE_KEY = "IdStateKey"
        const val SIGN_IN_REQUEST_TOKEN_KEY = "SignInRequestTokenKey"
    }
}
