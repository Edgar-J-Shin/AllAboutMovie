package com.dcs.data.repository

import androidx.annotation.WorkerThread
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.dcs.data.MovieRanking
import com.dcs.data.di.IoDispatcher
import com.dcs.data.model.mapper.toEntity
import com.dcs.data.pagingsource.MoviePagingSource
import com.dcs.data.remote.datasource.MovieRemoteDataSource
import com.dcs.data.remote.model.MoviesResponse
import com.dcs.domain.model.MovieEntity
import com.dcs.domain.repository.MovieRepository
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
        val result = movieRemoteDataSource.fetchMoviesByTopRated().asResult { response ->
            (response.data as MoviesResponse).results.map { movieResult ->
                movieResult.toEntity()
            }
        }

        emit(result)

    }.flowOn(ioDispatcher)

    override fun getMoviesByTrending(timeWindow: String): Flow<PagingData<MovieEntity>> =
        Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = DEFAULT_PAGE_SIZE),
            pagingSourceFactory = {
                MoviePagingSource(
                    movieRanking = MovieRanking.Trending(timeWindow = timeWindow),
                    pageSize = DEFAULT_PAGE_SIZE,
                    movieRemoteDataSource = movieRemoteDataSource
                )
            }
        ).flow

    override fun getMoviesByPopular(mediaType: String): Flow<PagingData<MovieEntity>> =
        Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = DEFAULT_PAGE_SIZE),
            pagingSourceFactory = {
                MoviePagingSource(
                    movieRanking = MovieRanking.Popular(mediaType = mediaType),
                    pageSize = DEFAULT_PAGE_SIZE,
                    movieRemoteDataSource = movieRemoteDataSource
                )
            }
        ).flow

    override fun getMoviesByTopRated(): Flow<PagingData<MovieEntity>> =
        Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = DEFAULT_PAGE_SIZE),
            pagingSourceFactory = {
                MoviePagingSource(
                    movieRanking = MovieRanking.TopRated,
                    pageSize = DEFAULT_PAGE_SIZE,
                    movieRemoteDataSource = movieRemoteDataSource
                )
            }
        ).flow

    companion object {
        const val DEFAULT_PAGE_SIZE: Int = 20
    }
}