package com.dcs.data.remote.datasource

import com.dcs.data.model.MovieType
import com.dcs.data.remote.model.MoviesResponse
import com.dcs.data.remote.model.RemoteMovie
import com.dcs.data.remote.network.NetworkResponse
import com.dcs.data.remote.service.MovieService
import com.dcs.domain.model.MovieId
import com.dcs.domain.model.MediaType
import com.dcs.domain.model.TimeWindow
import javax.inject.Inject

class MovieRemoteDataSourceImpl @Inject constructor(
    private val movieService: MovieService,
) : MovieRemoteDataSource {

    override suspend fun getMovies(
        movieType: MovieType,
        page: Int,
        language: String,
    ): Result<MoviesResponse> {
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
            it.data as MoviesResponse
        }
    }

    private suspend fun getMoviesByTrending(
        timeWindow: TimeWindow,
        page: Int,
        language: String,
    ): NetworkResponse<MoviesResponse> =
        movieService.fetchMoviesByTrending(
            timeWindow = timeWindow.value,
            page = page,
            language = language
        )

    private suspend fun getMoviesByPopular(
        mediaType: MediaType,
        page: Int,
        language: String,
    ): NetworkResponse<MoviesResponse> =
        movieService.fetchMoviesByPopular(
            mediaType = mediaType.value,
            page = page,
            language = language
        )

    private suspend fun getMoviesByTopRated(
        page: Int,
        language: String,
    ): NetworkResponse<MoviesResponse> =
        movieService.fetchMoviesByTopRated(
            page = page,
            language = language
        )

    private suspend fun getMoviesByNowPlaying(
        page: Int,
        language: String,
    ): NetworkResponse<MoviesResponse> =
        movieService.fetchMoviesByNowPlaying(
            page = page,
            language = language
        )

    private suspend fun getMoviesByUpcoming(
        page: Int,
        language: String,
    ): NetworkResponse<MoviesResponse> =
        movieService.fetchMoviesByUpcoming(
            page = page,
            language = language
        )

    private suspend fun getSearchContents(
        query: String,
        page: Int,
        language: String,
    ): NetworkResponse<MoviesResponse> =
        movieService.fetchSearchMultiByQuery(
            query = query,
            page = page,
            language = language
        )

    override suspend fun getMovieById(
        movieId: MovieId,
        language: String,
    ): Result<RemoteMovie> =
        movieService.fetchMovieDetails(
            movieId = movieId.value,
            language = language
        ).asResult {
            it.data as RemoteMovie
        }
}
