package com.example.data.repository

import androidx.annotation.WorkerThread
import com.example.data.di.IoDispatcher
import com.example.data.model.mapper.toMovieEntity
import com.example.data.remote.datasource.MovieRemoteDataSource
import com.example.data.remote.model.MoviesResponse
import com.example.domain.model.MovieEntity
import com.example.domain.repository.MovieRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val movieRemoteDataSource: MovieRemoteDataSource,
    @IoDispatcher val ioDispatcher: CoroutineDispatcher
) : MovieRepository {

    @WorkerThread
    override suspend fun fetchMoviesByTopRated(): Flow<Result<List<MovieEntity>>> = flow {
        val result = movieRemoteDataSource.fetchMoviesByTopRated().handleResponse { response ->
            (response.data as MoviesResponse).results.map { movieResult ->
                movieResult.toMovieEntity()
            }
        }

        emit(result)

    }.flowOn(ioDispatcher)
}