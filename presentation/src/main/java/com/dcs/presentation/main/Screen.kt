package com.dcs.presentation.main

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class Screen(
    val route: String,
    val navArguments: List<NamedNavArgument> = emptyList()
) {
    data object Home : Screen(route = ROUTE_HOME)

    data object Search : Screen(route = ROUTE_SEARCH)

    data object People : Screen(route = ROUTE_PEOPLE)

    data object Setting : Screen(route = ROUTE_SETTING)

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