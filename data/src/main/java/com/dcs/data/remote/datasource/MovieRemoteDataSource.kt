package com.dcs.data.remote.datasource

import com.dcs.data.remote.model.MoviesResponse
import com.dcs.data.remote.network.NetworkResponse

interface MovieRemoteDataSource {
    suspend fun fetchMoviesByTopRated(): NetworkResponse<MoviesResponse>

    suspend fun getMoviesByTrending(timeWindow: String, page: Int, language: String): Result<MoviesResponse>

    suspend fun getMoviesByPopular(mediaType: String, page: Int, language: String): Result<MoviesResponse>

    suspend fun getMoviesByTopRated(page: Int, language: String): Result<MoviesResponse>
}
