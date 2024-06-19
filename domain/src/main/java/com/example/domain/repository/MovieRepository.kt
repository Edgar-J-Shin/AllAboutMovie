package com.example.domain.repository

import androidx.paging.PagingData
import com.example.domain.model.MovieEntity
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    suspend fun fetchMoviesByTopRated(): Flow<Result<List<MovieEntity>>>

    suspend fun getMoviesByTopRated(): Flow<PagingData<MovieEntity>>
}