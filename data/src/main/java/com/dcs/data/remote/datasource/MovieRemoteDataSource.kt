package com.dcs.data.remote.datasource

import com.dcs.data.model.MovieType
import com.dcs.data.remote.model.MoviesResponse

interface MovieRemoteDataSource {

    suspend fun getMovies(movieType: MovieType, page: Int, language: String): Result<MoviesResponse>
}
