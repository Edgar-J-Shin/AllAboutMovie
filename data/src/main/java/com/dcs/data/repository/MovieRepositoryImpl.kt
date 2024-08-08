package com.dcs.data.repository

import androidx.annotation.WorkerThread
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.dcs.data.Trend
import com.dcs.data.di.IoDispatcher
import com.dcs.data.local.datasource.KeywordLocalDataSource
import com.dcs.data.model.mapper.toEntity
import com.dcs.data.pagingsource.MoviePagingSource
import com.dcs.data.remote.datasource.MovieRemoteDataSource
import com.dcs.data.remote.model.MoviesResponse
import com.dcs.domain.model.Keyword
import com.dcs.domain.model.Movie
import com.dcs.domain.repository.MovieRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val movieRemoteDataSource: MovieRemoteDataSource,
    private val keywordLocalDataSource: KeywordLocalDataSource,
    @IoDispatcher val ioDispatcher: CoroutineDispatcher,
) : MovieRepository {

    @WorkerThread
    override suspend fun fetchMoviesByTopRated(): Flow<Result<List<Movie>>> = flow {
        val result = movieRemoteDataSource.fetchMoviesByTopRated().asResult { response ->
            (response.data as MoviesResponse).results.map { movieResult ->
                movieResult.toEntity()
            }
        }

        emit(result)

    }.flowOn(ioDispatcher)

    @WorkerThread
    override fun getMoviesByTopRated(): Flow<PagingData<Movie>> =
        Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = DEFAULT_PAGE_SIZE),
            pagingSourceFactory = {
                MoviePagingSource(
                    trend = Trend.TopRated,
                    movieRemoteDataSource = movieRemoteDataSource
                )
            }
        ).flow

    @WorkerThread
    override fun getMoviesByTrending(timeWindow: String): Flow<PagingData<Movie>> =
        Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = DEFAULT_PAGE_SIZE),
            pagingSourceFactory = {
                MoviePagingSource(
                    trend = Trend.Trending(timeWindow = timeWindow),
                    movieRemoteDataSource = movieRemoteDataSource
                )
            }
        ).flow

    @WorkerThread
    override fun getMoviesByPopular(mediaType: String): Flow<PagingData<Movie>> =
        Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = DEFAULT_PAGE_SIZE),
            pagingSourceFactory = {
                MoviePagingSource(
                    trend = Trend.Popular(mediaType = mediaType),
                    movieRemoteDataSource = movieRemoteDataSource
                )
            }
        ).flow

    override fun getMoviesByUpcoming(): Flow<PagingData<Movie>> =
        Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = DEFAULT_PAGE_SIZE),
            pagingSourceFactory = {
                MoviePagingSource(
                    trend = Trend.Upcoming,
                    movieRemoteDataSource = movieRemoteDataSource
                )
            }
        ).flow

    override fun getSearchContents(query: String): Flow<PagingData<Movie>> =
        Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = DEFAULT_PAGE_SIZE),
            pagingSourceFactory = {
                MoviePagingSource(
                    trend = Trend.Search(query),
                    movieRemoteDataSource = movieRemoteDataSource
                )
            }
        )
            .flow
            .onEach {
                keywordLocalDataSource.insertKeyword(Keyword(query))
            }

    companion object {
        const val DEFAULT_PAGE_SIZE: Int = 20
    }
}


