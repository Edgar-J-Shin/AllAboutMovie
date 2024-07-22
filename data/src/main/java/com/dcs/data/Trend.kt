package com.dcs.data

sealed class Trend {
    data class Trending(val timeWindow: String) : Trend()

    data class Popular(val mediaType: String) : Trend()

    data object TopRated : Trend()

    data object Upcoming : Trend()

    data class Search(val query: String) : Trend()
}