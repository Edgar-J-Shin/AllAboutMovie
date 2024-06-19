package com.example.data.remote.datasource

import com.example.data.remote.model.MoviesResponse
import com.example.data.remote.network.NetworkResponse

interface MovieRemoteDataSource {
    suspend fun fetchMoviesByTopRated(): NetworkResponse<MoviesResponse>

    suspend fun getMoviesByTopRated(page: Int): NetworkResponse<MoviesResponse>
}
