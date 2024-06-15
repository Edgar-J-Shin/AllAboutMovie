package com.example.data.repository

import androidx.annotation.WorkerThread
import com.example.data.model.MovieResult
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    @WorkerThread
    suspend fun fetchMoviesByTopRated(
        onStart: () -> Unit,
        onCompletion: () -> Unit,
        onError: (String?) -> Unit
    ): Flow<List<MovieResult>>
}