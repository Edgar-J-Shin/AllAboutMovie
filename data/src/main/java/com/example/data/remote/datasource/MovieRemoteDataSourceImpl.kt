package com.example.data.remote.datasource

import com.example.data.remote.model.MoviesResponse
import com.example.data.remote.network.NetworkResponse
import com.example.data.remote.service.MovieService
import javax.inject.Inject

class MovieRemoteDataSourceImpl @Inject constructor(
    private val movieService: MovieService
) : MovieRemoteDataSource {
    override suspend fun fetchMoviesByTopRated(): NetworkResponse<MoviesResponse> =
        movieService.fetchMoviesByTopRated()
}