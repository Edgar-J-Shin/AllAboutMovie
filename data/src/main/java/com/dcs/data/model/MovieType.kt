package com.dcs.data.model

import com.dcs.domain.model.MediaType
import com.dcs.domain.model.TimeWindow

sealed class MovieType {
    data class Trending(val timeWindow: TimeWindow) : MovieType()

    data class Popular(val mediaType: MediaType) : MovieType()

    data object TopRated : MovieType()

    data object Upcoming : MovieType()

    data class Search(val query: String) : MovieType()
}
