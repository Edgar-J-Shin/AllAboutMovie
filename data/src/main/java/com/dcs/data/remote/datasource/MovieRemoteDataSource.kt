package com.dcs.data.remote.datasource

import com.dcs.data.model.MovieType
import com.dcs.data.remote.model.GetMovieDetailResponse
import com.dcs.data.remote.model.GetMoviesResponse
import com.dcs.domain.model.MovieId

interface MovieRemoteDataSource {

    suspend fun getMovies(
        movieType: MovieType,
        page: Int,
        language: String,
    ): Result<GetMoviesResponse>

    suspend fun getMovieDetailById(
        movieId: MovieId,
        appendToResponse: String,
        language: String,
    ): Result<GetMovieDetailResponse>
}
