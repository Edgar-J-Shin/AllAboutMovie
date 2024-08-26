package com.dcs.data.remote.datasource

import com.dcs.data.model.MovieType
import com.dcs.data.remote.model.MoviesResponse
import com.dcs.data.remote.model.RemoteMovie
import com.dcs.domain.model.MovieId

interface MovieRemoteDataSource {

    suspend fun getMovies(
        movieType: MovieType,
        page: Int,
        language: String,
    ): Result<MoviesResponse>

    suspend fun getMovieById(
        movieId: MovieId,
        language: String = "en-US",
    ): Result<RemoteMovie>
}
