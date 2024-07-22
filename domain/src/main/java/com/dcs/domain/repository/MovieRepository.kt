package com.dcs.domain.repository

import androidx.paging.PagingData
import com.dcs.domain.model.MovieEntity
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    suspend fun fetchMoviesByTopRated(): Flow<Result<List<MovieEntity>>>

    fun getMoviesByTopRated(): Flow<PagingData<MovieEntity>>

    fun getMoviesByTrending(timeWindow: String): Flow<PagingData<MovieEntity>>

    fun getMoviesByPopular(mediaType: String): Flow<PagingData<MovieEntity>>

    fun getMoviesByUpcoming(): Flow<PagingData<MovieEntity>>

    fun getSearchMulti(query: String): Flow<PagingData<MovieEntity>>
}
