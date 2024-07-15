package com.dcs.data

sealed class MovieRanking {
    data class Trending(val timeWindow: String) : MovieRanking()

    data class Popular(val mediaType: String) : MovieRanking()

    data object TopRated : MovieRanking()
}