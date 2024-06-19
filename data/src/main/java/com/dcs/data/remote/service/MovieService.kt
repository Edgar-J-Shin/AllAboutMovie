package com.dcs.data.remote.service

import com.dcs.data.remote.model.MoviesResponse
import com.dcs.data.remote.network.NetworkResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {

    /**
     * 상위 순위(Top Rated) 순으로 영화 목록을 가져온다.
     *
     * @param page
     * @param language
     *
     * @return [MoviesResponse]
     */
    @GET("movie/top_rated")
    suspend fun fetchMoviesByTopRated(
        @Query("page") page: Int = 1,
        @Query("language") language: String = "en-US"
    ): NetworkResponse<MoviesResponse>

    /**
     * 트렌드 순위(Trending) 순으로 영화 목록을 가져온다.
     *
     * @param timeWindow "day" or "week"
     * @param page
     * @param language
     *
     * @return [MoviesResponse]
     */
    @GET("movie/trending/movie/{time_window}")
    suspend fun fetchMoviesByTrending(
        @Path("time_window") timeWindow: String = "day",
        @Query("page") page: Int = 1,
        @Query("language") language: String = "en-US"
    ): NetworkResponse<MoviesResponse>
}