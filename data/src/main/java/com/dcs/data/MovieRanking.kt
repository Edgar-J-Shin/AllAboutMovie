package com.dcs.data

sealed class MovieRanking {
    data class Trending(val timeWindow: String) : MovieRanking()

    data object TopRated : MovieRanking()
}