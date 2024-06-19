package com.example.data.remote.service

import com.example.data.remote.model.MoviesResponse
import com.example.data.remote.network.NetworkResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService {

    /**
     * 상위 순위(Top Rated) 순으로 영화 목록을 가져온다.
     *
     * @return [MoviesResponse]
     */
    @GET("movie/top_rated")
    suspend fun fetchMoviesByTopRated(
        @Query("page") page: Int,
        @Query("language") language: String = "en-US"
    ): NetworkResponse<MoviesResponse>
}