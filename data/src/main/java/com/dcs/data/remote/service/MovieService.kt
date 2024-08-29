package com.dcs.data.remote.service

import com.dcs.data.remote.model.GetMovieDetailResponse
import com.dcs.data.remote.model.GetMoviesResponse
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
     * @return [GetMoviesResponse]
     */
    @GET("movie/top_rated")
    suspend fun fetchMoviesByTopRated(
        @Query("page") page: Int = 1,
        @Query("language") language: String = "en-US",
    ): NetworkResponse<GetMoviesResponse>

    /**
     * Get the trending movies on TMDB.
     *
     * @param timeWindow ["day", "week"]
     * @param page
     * @param language
     *
     * @return [GetMoviesResponse]
     */
    @GET("trending/movie/{time_window}")
    suspend fun fetchMoviesByTrending(
        @Path("time_window") timeWindow: String = "day",
        @Query("page") page: Int = 1,
        @Query("language") language: String = "en-US",
    ): NetworkResponse<GetMoviesResponse>

    /**
     * Get a list of movies or TV shows ordered by popularity.
     *
     * @param mediaType ["movie", "tv"]
     * @param page
     * @param language
     *
     * @return [GetMoviesResponse]
     */
    @GET("{media_type}/popular")
    suspend fun fetchMoviesByPopular(
        @Path("media_type") mediaType: String = "movie",
        @Query("page") page: Int = 1,
        @Query("language") language: String = "en-US",
    ): NetworkResponse<GetMoviesResponse>

    /**
     * Get a list of movies that are currently in theatres.
     *
     * @param page
     * @param language
     *
     * @return [GetMoviesResponse]
     */
    @GET("movie/now_playing")
    suspend fun fetchMoviesByNowPlaying(
        @Query("page") page: Int = 1,
        @Query("language") language: String = "en-US",
    ): NetworkResponse<GetMoviesResponse>

    /**
     * Get a list of movies that are being released soon.
     *
     * @param page
     * @param language
     *
     * @return [GetMoviesResponse]
     */
    @GET("movie/upcoming")
    suspend fun fetchMoviesByUpcoming(
        @Query("page") page: Int = 1,
        @Query("language") language: String = "en-US",
    ): NetworkResponse<GetMoviesResponse>

    /**
     * Search for movies by their original, translated and alternative titles.
     *
     * @param query
     * @param page
     * @param language
     *
     * @return [GetMoviesResponse]
     */
    @GET("search/movie")
    suspend fun fetchSearchMovieByQuery(
        @Query("query") query: String,
        @Query("include_adult") includeAdult: Boolean = false,
        @Query("page") page: Int = 1,
        @Query("language") language: String = "en-US",
    ): NetworkResponse<GetMoviesResponse>

    /**
     * Get the top level details of a movie by ID.
     *
     * @param movieId
     * @param language
     *
     * @return [GetMovieDetailResponse]
     */
    @GET("movie/{movie_id}")
    suspend fun fetchMovieDetailById(
        @Path("movie_id") movieId: Int,
        @Query("append_to_response") appendToResponse: String = "",
        @Query("language") language: String = "en-US",
    ): NetworkResponse<GetMovieDetailResponse>
}

