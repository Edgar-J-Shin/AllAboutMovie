package com.dcs.data.model

sealed class MovieType {

    data object Popular : MovieType()

    data object TopRated : MovieType()

    data object Upcoming : MovieType()

    data class Search(val query: String) : MovieType()
}
