package com.dcs.data.remote.datasource

import com.dcs.data.remote.model.MoviesResponse
import com.dcs.data.remote.network.NetworkResponse
import com.dcs.data.remote.service.MovieService
import javax.inject.Inject

class MovieRemoteDataSourceImpl @Inject constructor(
    private val movieService: MovieService
) : MovieRemoteDataSource {

    /**
     * Todo : 테스트용 제거 예정
     */
    override suspend fun fetchMoviesByTopRated(): NetworkResponse<MoviesResponse> =
        movieService.fetchMoviesByTopRated(0)

    override suspend fun getMoviesByTrending(timeWindow: String, page: Int, language: String): Result<MoviesResponse> =
        movieService.fetchMoviesByTrending(
            timeWindow = timeWindow,
            page = page,
            language = language
        ).asResult {
            it.data as MoviesResponse
        }

    override suspend fun getMoviesByPopular(mediaType: String, page: Int, language: String): Result<MoviesResponse> =
        movieService.fetchMoviesByPopular(
            mediaType = mediaType,
            page = page,
            language = language
        ).asResult {
            it.data as MoviesResponse
        }

    override suspend fun getMoviesByTopRated(page: Int, language: String): Result<MoviesResponse> =
        movieService.fetchMoviesByTopRated(
            page = page,
            language = language
        ).asResult {
            it.data as MoviesResponse
        }

    override suspend fun getMoviesByNowPlaying(page: Int, language: String): Result<MoviesResponse> =
        movieService.fetchMoviesByNowPlaying(
            page = page,
            language = language
        ).asResult {
            it.data as MoviesResponse
        }

    override suspend fun getMoviesByUpcoming(page: Int, language: String): Result<MoviesResponse> =
        movieService.fetchMoviesByUpcoming(
            page = page,
            language = language
        ).asResult {
            it.data as MoviesResponse
        }

    override suspend fun getSearchMulti(query: String, page: Int, language: String): Result<MoviesResponse> =
        movieService.fetchSearchMultiByQuery(
            query = query,
            page = page,
            language = language
        ).asResult {
            it.data as MoviesResponse
        }
}