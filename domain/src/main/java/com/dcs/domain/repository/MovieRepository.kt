package com.dcs.domain.repository

import androidx.paging.PagingData
import com.dcs.domain.model.MovieEntity
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    suspend fun fetchMoviesByTopRated(): Flow<Result<List<MovieEntity>>>

    suspend fun getMoviesByTopRated(): Flow<PagingData<MovieEntity>>
}