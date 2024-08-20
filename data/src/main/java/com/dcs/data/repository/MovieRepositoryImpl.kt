package com.dcs.data.repository

import androidx.annotation.WorkerThread
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.dcs.data.local.datasource.KeywordLocalDataSource
import com.dcs.data.model.MovieType
import com.dcs.data.pagingsource.MoviePagingSource
import com.dcs.data.remote.datasource.MovieRemoteDataSource
import com.dcs.domain.model.Keyword
import com.dcs.domain.model.MediaType
import com.dcs.domain.model.Movie
import com.dcs.domain.model.MovieId
import com.dcs.domain.model.TimeWindow
import com.dcs.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val movieRemoteDataSource: MovieRemoteDataSource,
    private val keywordLocalDataSource: KeywordLocalDataSource,
) : MovieRepository {

    @WorkerThread
    override fun getMoviesByTopRated(): Flow<PagingData<Movie>> =
        Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = DEFAULT_PAGE_SIZE),
            pagingSourceFactory = {
                MoviePagingSource(
                    movieType = MovieType.TopRated,
                    movieRemoteDataSource = movieRemoteDataSource
                )
            }
        ).flow

    @WorkerThread
    override fun getMoviesByTrending(timeWindow: TimeWindow): Flow<PagingData<Movie>> =
        Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = DEFAULT_PAGE_SIZE),
            pagingSourceFactory = {
                MoviePagingSource(
                    movieType = MovieType.Trending(timeWindow = timeWindow),
                    movieRemoteDataSource = movieRemoteDataSource
                )
            }
        ).flow

    @WorkerThread
    override fun getMoviesByPopular(mediaType: MediaType): Flow<PagingData<Movie>> =
        Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = DEFAULT_PAGE_SIZE),
            pagingSourceFactory = {
                MoviePagingSource(
                    movieType = MovieType.Popular(mediaType = mediaType),
                    movieRemoteDataSource = movieRemoteDataSource
                )
            }
        ).flow

    override fun getMoviesByUpcoming(): Flow<PagingData<Movie>> =
        Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = DEFAULT_PAGE_SIZE),
            pagingSourceFactory = {
                MoviePagingSource(
                    movieType = MovieType.Upcoming,
                    movieRemoteDataSource = movieRemoteDataSource
                )
            }
        ).flow

    override fun getSearchContents(query: String): Flow<PagingData<Movie>> =
        Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = DEFAULT_PAGE_SIZE),
            pagingSourceFactory = {
                MoviePagingSource(
                    movieType = MovieType.Search(query),
                    movieRemoteDataSource = movieRemoteDataSource
                )
            }
        )
            .flow
            .onEach {
                keywordLocalDataSource.insertKeyword(Keyword(query))
            }

    override fun getMovieById(
        movieId: MovieId,
    ): Flow<Movie> = flow {
        val result = movieRemoteDataSource
            .getMovieById(
                movieId = movieId
            )
            .getOrThrow()
            .toEntity()

        emit(result)
    }.flowOn(ioDispatcher)

    companion object {
        const val DEFAULT_PAGE_SIZE: Int = 20
    }
}


