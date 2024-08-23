package com.dcs.domain.repository

import androidx.paging.PagingData
import com.dcs.domain.model.MediaType
import com.dcs.domain.model.Movie
import com.dcs.domain.model.TimeWindow
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    fun getMoviesByTopRated(): Flow<PagingData<Movie>>

    fun getMoviesByTrending(timeWindow: TimeWindow): Flow<PagingData<Movie>>

    fun getMoviesByPopular(mediaType: MediaType): Flow<PagingData<Movie>>

    fun getMoviesByUpcoming(): Flow<PagingData<Movie>>

    fun getSearchContents(query: String): Flow<PagingData<Movie>>
}
