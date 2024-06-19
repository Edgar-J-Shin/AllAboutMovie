package com.dcs.data.remote.datasource

import com.dcs.data.remote.model.MoviesResponse
import com.dcs.data.remote.network.NetworkResponse

interface MovieRemoteDataSource {
    suspend fun fetchMoviesByTopRated(): NetworkResponse<MoviesResponse>

    suspend fun getMoviesByTopRated(page: Int): Result<MoviesResponse>

    suspend fun getMoviesByTrending(timeWindow:String, page: Int): Result<MoviesResponse>
}
