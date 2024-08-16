package com.dcs.domain.repository

import androidx.paging.PagingData
import com.dcs.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    suspend fun fetchMoviesByTopRated(): Flow<Result<List<Movie>>>

    fun getMoviesByTopRated(): Flow<PagingData<Movie>>

    fun getMoviesByTrending(timeWindow: String): Flow<PagingData<Movie>>

    fun getMoviesByPopular(mediaType: String): Flow<PagingData<Movie>>

    fun getMoviesByUpcoming(): Flow<PagingData<Movie>>

    fun getSearchContents(query: String): Flow<PagingData<Movie>>
}
