package com.dcs.data.remote.datasource

import com.dcs.data.model.MovieType
import com.dcs.data.remote.model.GetMovieDetailResponse
import com.dcs.data.remote.model.GetMoviesResponse
import com.dcs.data.remote.network.NetworkResponse
import com.dcs.data.remote.service.MovieService
import com.dcs.domain.model.MediaType
import com.dcs.domain.model.MovieId
import com.dcs.domain.model.TimeWindow
import javax.inject.Inject

class MovieRemoteDataSourceImpl @Inject constructor(
    private val movieService: MovieService,
) : MovieRemoteDataSource {

    override suspend fun getMovies(
        movieType: MovieType,
        page: Int,
        language: String,
    ): Result<GetMoviesResponse> {
        return when (movieType) {
            is MovieType.Trending -> {
                getMoviesByTrending(movieType.timeWindow, page, language)
            }

            is MovieType.Popular -> {
                getMoviesByPopular(movieType.mediaType, page, language)
            }

            MovieType.TopRated -> {
                getMoviesByTopRated(page, language)
            }

            MovieType.Upcoming -> {
                getMoviesByUpcoming(page, language)
            }

            is MovieType.Search -> {
                getSearchContents(movieType.query, page, language)
            }
        }.asResult {
            it.data as GetMoviesResponse
        }
    }

    private suspend fun getMoviesByTrending(
        timeWindow: TimeWindow,
        page: Int,
        language: String,
    ): NetworkResponse<GetMoviesResponse> =
        movieService.fetchMoviesByTrending(
            timeWindow = timeWindow.value,
            page = page,
            language = language
        )

    private suspend fun getMoviesByPopular(
        mediaType: MediaType,
        page: Int,
        language: String,
    ): NetworkResponse<GetMoviesResponse> =
        movieService.fetchMoviesByPopular(
            mediaType = mediaType.value,
            page = page,
            language = language
        )

    private suspend fun getMoviesByTopRated(
        page: Int,
        language: String,
    ): NetworkResponse<GetMoviesResponse> =
        movieService.fetchMoviesByTopRated(
            page = page,
            language = language
        )

    private suspend fun getMoviesByNowPlaying(
        page: Int,
        language: String,
    ): NetworkResponse<GetMoviesResponse> =
        movieService.fetchMoviesByNowPlaying(
            page = page,
            language = language
        )

    private suspend fun getMoviesByUpcoming(
        page: Int,
        language: String,
    ): NetworkResponse<GetMoviesResponse> =
        movieService.fetchMoviesByUpcoming(
            page = page,
            language = language
        )

    private suspend fun getSearchContents(
        query: String,
        page: Int,
        language: String,
    ): NetworkResponse<GetMoviesResponse> =
        movieService.fetchSearchMovieByQuery(
            query = query,
            page = page,
            language = language
        )

    override suspend fun getMovieDetailById(
        movieId: MovieId,
        appendToResponse: String,
        language: String,
    ): Result<GetMovieDetailResponse> =
        movieService.fetchMovieDetailById(
            movieId = movieId.value,
            appendToResponse = appendToResponse,
            language = language
        ).asResult {
            it.data as GetMovieDetailResponse
        }
}
