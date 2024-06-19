package com.example.data.repository

import androidx.annotation.WorkerThread
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.data.di.IoDispatcher
import com.example.data.model.mapper.toEntity
import com.example.data.pagingsource.MoviePagingSource
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
                movieResult.toEntity()
            }
        }

        emit(result)

    }.flowOn(ioDispatcher)

    override suspend fun getMoviesByTopRated(): Flow<PagingData<MovieEntity>> =
        Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = 20),
            pagingSourceFactory = {
                MoviePagingSource(
                    pageSize = 20,
                    movieRemoteDataSource = movieRemoteDataSource
                )
            }
        ).flow
}