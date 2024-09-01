package com.dcs.data.remote.datasource

import com.dcs.data.model.MediaContentType
import com.dcs.data.model.MovieType
import com.dcs.data.remote.model.GetMediaContentsResponse
import com.dcs.data.remote.model.GetMovieDetailResponse
import com.dcs.data.remote.model.GetMoviesResponse
import com.dcs.data.remote.network.NetworkResponse
import com.dcs.data.remote.service.MovieService
import com.dcs.domain.model.MediaContentId
import com.dcs.domain.model.TimeWindow
import javax.inject.Inject

class MovieRemoteDataSourceImpl @Inject constructor(
    private val movieService: MovieService,
) : MovieRemoteDataSource {

    override suspend fun getMediaContents(
        mediaContentType: MediaContentType,
        page: Int,
        language: String,
    ): Result<GetMediaContentsResponse> {
        return when (mediaContentType) {
            is MediaContentType.Trending -> {
                getTrendingMovies(mediaContentType.timeWindow, page, language)
            }
        }.asResult {
            it.data as GetMediaContentsResponse
        }
    }

    override suspend fun getMovies(
        movieType: MovieType,
        page: Int,
        language: String,
    ): Result<GetMoviesResponse> {
        return when (movieType) {

            MovieType.Popular -> {
                getPopularMovies(page, language)
            }

            MovieType.TopRated -> {
                getTopRatedMovies(page, language)
            }

            MovieType.Upcoming -> {
                getUpcomingMovies(page, language)
            }

            is MovieType.Search -> {
                getSearchMoviesByQuery(movieType.query, page, language)
            }
        }.asResult {
            it.data as GetMoviesResponse
        }
    }

    override suspend fun getMovieDetailById(
        mediaContentId: MediaContentId,
        appendToResponse: String,
        language: String,
    ): Result<GetMovieDetailResponse> =
        movieService.fetchMovieDetailById(
            movieId = mediaContentId.value,
            appendToResponse = appendToResponse,
            language = language
        ).asResult {
            it.data as GetMovieDetailResponse
        }

    private suspend fun getTrendingMovies(
        timeWindow: TimeWindow,
        page: Int,
        language: String,
    ): NetworkResponse<GetMediaContentsResponse> =
        movieService.fetchTrendingMovies(
            timeWindow = timeWindow.value,
            page = page,
            language = language
        )

    private suspend fun getPopularMovies(
        page: Int,
        language: String,
    ): NetworkResponse<GetMoviesResponse> =
        movieService.fetchPopularMovies(
            page = page,
            language = language
        )

    private suspend fun getTopRatedMovies(
        page: Int,
        language: String,
    ): NetworkResponse<GetMoviesResponse> =
        movieService.fetchTopRatedMovies(
            page = page,
            language = language
        )

    private suspend fun getNowPlayingMovies(
        page: Int,
        language: String,
    ): NetworkResponse<GetMoviesResponse> =
        movieService.fetchNowPlayingMovies(
            page = page,
            language = language
        )

    private suspend fun getUpcomingMovies(
        page: Int,
        language: String,
    ): NetworkResponse<GetMoviesResponse> =
        movieService.fetchUpcomingMovies(
            page = page,
            language = language
        )

    private suspend fun getSearchMoviesByQuery(
        query: String,
        page: Int,
        language: String,
    ): NetworkResponse<GetMoviesResponse> =
        movieService.fetchSearchMoviesByQuery(
            query = query,
            page = page,
            language = language
        )
}
