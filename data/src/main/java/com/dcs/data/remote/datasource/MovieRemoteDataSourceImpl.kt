package com.dcs.data.remote.datasource

import com.dcs.data.remote.model.MoviesResponse
import com.dcs.data.remote.network.NetworkResponse
import com.dcs.data.remote.service.MovieService
import javax.inject.Inject

class MovieRemoteDataSourceImpl @Inject constructor(
    private val movieService: MovieService
) : MovieRemoteDataSource {

    /**
     * For test
     */
    override suspend fun fetchMoviesByTopRated(): NetworkResponse<MoviesResponse> =
        movieService.fetchMoviesByTopRated(0)

    override suspend fun getMoviesByTopRated(page: Int): Result<MoviesResponse> =
        movieService.fetchMoviesByTopRated(
            page = page
        ).handleNetworkResponse {
            it.data as MoviesResponse
        }

    override suspend fun getMoviesByTrending(timeWindow: String, page: Int): Result<MoviesResponse> =
        movieService.fetchMoviesByTrending(
            timeWindow = timeWindow,
            page = page
        ).handleNetworkResponse {
            it.data as MoviesResponse
        }
}