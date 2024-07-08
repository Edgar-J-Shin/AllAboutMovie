package com.dcs.presentation.main

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.dcs.presentation.R

sealed class Screen(
    val route: String,
    val navArguments: List<NamedNavArgument> = emptyList(),
) {

    sealed class Main(
        route: String,
        @StringRes val titleResId: Int,
        @DrawableRes val iconResId: Int,
    ) : Screen(route, emptyList()) {
        data object Home : Main(
            route = ROUTE_HOME,
            titleResId = R.string.home,
            iconResId = R.drawable.ic_home
        )

        data object Search : Main(
            route = ROUTE_SEARCH,
            titleResId = R.string.trend,
            iconResId = R.drawable.ic_trend
        )

        data object People : Main(
            route = ROUTE_PEOPLE,
            titleResId = R.string.people,
            iconResId = R.drawable.ic_people
        )

        data object Setting : Main(
            route = ROUTE_SETTING,
            titleResId = R.string.setting,
            iconResId = R.drawable.ic_setting
        )
    }

    data object DetailItem : Screen(
        route = "$ROUTE_DETAIL/{$ID_SAVED_STATE_KEY}",
        navArguments = listOf(navArgument(ID_SAVED_STATE_KEY) {
            type = NavType.StringType
        })
    ) {
        fun createRoute(itemId: String) = "$ROUTE_DETAIL/${itemId}"

    }

    companion object {
        const val ROUTE_HOME = "home"
        const val ROUTE_SEARCH = "search"
        const val ROUTE_PEOPLE = "people"
        const val ROUTE_SETTING = "setting"
        const val ROUTE_DETAIL = "detail"

        const val ID_SAVED_STATE_KEY = "IdStateKey"
    }
}
