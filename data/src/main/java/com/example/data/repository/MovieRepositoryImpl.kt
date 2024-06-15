package com.example.data.repository

import com.example.data.di.IoDispatcher
import com.example.data.model.MovieResult
import com.example.data.remote.datasource.MovieRemoteDataSource
import com.example.data.remote.model.MoviesResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import timber.log.Timber
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val movieRemoteDataSource: MovieRemoteDataSource,
    @IoDispatcher val ioDispatcher: CoroutineDispatcher
) : MovieRepository {
    override suspend fun fetchMoviesByTopRated(
        onStart: () -> Unit,
        onCompletion: () -> Unit,
        onError: (String?) -> Unit
    ): Flow<List<MovieResult>> = flow {
        movieRemoteDataSource.fetchMoviesByTopRated().handleResponse {
            it.data as MoviesResponse
        }.onSuccess {
            emit(it.results)
        }.onFailure {
            Timber.e(it)
            onError(it.message)
        }
    }.onStart {
        onStart()
    }.onCompletion {
        onCompletion()
    }.flowOn(ioDispatcher)
}