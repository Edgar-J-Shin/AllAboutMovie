package com.dcs.data.repository

import androidx.annotation.WorkerThread
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.dcs.data.di.IoDispatcher
import com.dcs.data.local.datasource.KeywordLocalDataSource
import com.dcs.data.model.MediaContentType
import com.dcs.data.model.MovieType
import com.dcs.data.model.TvShowType
import com.dcs.data.model.mapper.toEntity
import com.dcs.data.pagingsource.MediaContentPagingSource
import com.dcs.data.pagingsource.MoviePagingSource
import com.dcs.data.pagingsource.TvShowPagingSource
import com.dcs.data.remote.datasource.MovieRemoteDataSource
import com.dcs.data.remote.datasource.TvShowRemoteDataSource
import com.dcs.domain.model.Keyword
import com.dcs.domain.model.MediaContentId
import com.dcs.domain.model.MediaPolymorphic
import com.dcs.domain.model.MediaType
import com.dcs.domain.model.MovieDetail
import com.dcs.domain.model.TimeWindow
import com.dcs.domain.model.TvShowDetail
import com.dcs.domain.repository.MediaContentRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class MediaContentRepositoryImpl @Inject constructor(
    private val movieRemoteDataSource: MovieRemoteDataSource,
    private val tvShowRemoteDataSource: TvShowRemoteDataSource,
    private val keywordLocalDataSource: KeywordLocalDataSource,
    @IoDispatcher val ioDispatcher: CoroutineDispatcher,
) : MediaContentRepository {

    @WorkerThread
    override fun getTrendingMediaContents(
        mediaType: MediaType,
        timeWindow: TimeWindow,
    ): Flow<PagingData<MediaPolymorphic>> =
        Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = DEFAULT_PAGE_SIZE),
            pagingSourceFactory = {
                MediaContentPagingSource(
                    mediaContentType = MediaContentType.Trending(mediaType = mediaType, timeWindow = timeWindow),
                    movieRemoteDataSource = movieRemoteDataSource
                )
            }
        ).flow

    @WorkerThread
    override fun getPopularMovies(): Flow<PagingData<MediaPolymorphic.Movie>> =
        Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = DEFAULT_PAGE_SIZE),
            pagingSourceFactory = {
                MoviePagingSource(
                    movieType = MovieType.Popular,
                    movieRemoteDataSource = movieRemoteDataSource
                )
            }
        ).flow

    @WorkerThread
    override fun getTopRatedMovies(): Flow<PagingData<MediaPolymorphic.Movie>> =
        Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = DEFAULT_PAGE_SIZE),
            pagingSourceFactory = {
                MoviePagingSource(
                    movieType = MovieType.TopRated,
                    movieRemoteDataSource = movieRemoteDataSource
                )
            }
        ).flow

    override fun getUpcomingMovies(): Flow<PagingData<MediaPolymorphic.Movie>> =
        Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = DEFAULT_PAGE_SIZE),
            pagingSourceFactory = {
                MoviePagingSource(
                    movieType = MovieType.Upcoming,
                    movieRemoteDataSource = movieRemoteDataSource
                )
            }
        ).flow

    override fun getSearchContents(query: String): Flow<PagingData<MediaPolymorphic.Movie>> =
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
        mediaContentId: MediaContentId,
    ): Flow<MovieDetail> = flow {
        val result = movieRemoteDataSource
            .getMovieDetailById(
                mediaContentId = mediaContentId,
                appendToResponse = "credits",
                language = "en-US"
            )
            .getOrThrow()
            .toEntity()

        emit(result)
    }.flowOn(ioDispatcher)

    override fun getPopularTvShows(): Flow<PagingData<MediaPolymorphic.TvShow>> =
        Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = DEFAULT_PAGE_SIZE),
            pagingSourceFactory = {
                TvShowPagingSource(
                    tvShowType = TvShowType.Popular,
                    tvShowRemoteDataSource = tvShowRemoteDataSource,
                )
            }
        ).flow

    override fun getTvShowById(
        mediaContentId: MediaContentId,
    ): Flow<TvShowDetail> = flow {
        val result = tvShowRemoteDataSource
            .getTvShowDetailById(
                mediaContentId = mediaContentId,
                appendToResponse = "credits",
                language = "en-US"
            )
            .getOrThrow()
            .toEntity()

        emit(result)
    }.flowOn(ioDispatcher)

    companion object {
        const val DEFAULT_PAGE_SIZE: Int = 20
    }
}


