package com.dcs.data.remote.service

import com.dcs.data.remote.model.MoviesResponse
import com.dcs.data.remote.network.NetworkResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {

    /**
     * Get a list of movies ordered by rating.
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
     * Get the trending movies on TMDB.
     *
     * @param timeWindow ["day", "week"]
     * @param page
     * @param language
     *
     * @return [MoviesResponse]
     */
    @GET("trending/movie/{time_window}")
    suspend fun fetchMoviesByTrending(
        @Path("time_window") timeWindow: String = "day",
        @Query("page") page: Int = 1,
        @Query("language") language: String = "en-US"
    ): NetworkResponse<MoviesResponse>

    /**
     * Get a list of movies or TV shows ordered by popularity.
     *
     * @param mediaType ["movie", "tv"]
     * @param page
     * @param language
     *
     * @return [MoviesResponse]
     */
    @GET("{media_type}/popular")
    suspend fun fetchMoviesByPopular(
        @Path("media_type") mediaType: String = "movie",
        @Query("page") page: Int = 1,
        @Query("language") language: String = "en-US"
    ): NetworkResponse<MoviesResponse>

    /**
     * Get a list of movies that are currently in theatres.
     *
     * @param page
     * @param language
     *
     * @return [MoviesResponse]
     */
    @GET("movie/now_playing")
    suspend fun fetchMoviesByNowPlaying(
        @Query("page") page: Int = 1,
        @Query("language") language: String = "en-US"
    ): NetworkResponse<MoviesResponse>

    /**
     * Get a list of movies that are being released soon.
     *
     * @param page
     * @param language
     *
     * @return [MoviesResponse]
     */
    @GET("movie/upcoming")
    suspend fun fetchMoviesByUpcoming(
        @Query("page") page: Int = 1,
        @Query("language") language: String = "en-US"
    ): NetworkResponse<MoviesResponse>
}
