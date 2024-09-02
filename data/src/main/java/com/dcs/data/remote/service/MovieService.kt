package com.dcs.data.remote.service

import com.dcs.data.remote.model.GetMediaContentsResponse
import com.dcs.data.remote.model.GetMovieDetailResponse
import com.dcs.data.remote.model.GetMoviesResponse
import com.dcs.data.remote.model.GetTvShowsResponse
import com.dcs.data.remote.network.NetworkResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {

    /**
     * Get the trending movies on TMDB.
     *
     * @param timeWindow ["day", "week"]
     * @param page
     * @param language
     *
     * @return [GetMoviesResponse]
     */
    @GET("trending/{media_type}/{time_window}")
    suspend fun fetchTrendingMediaContents(
        @Path("media_type") mediaType: String,
        @Path("time_window") timeWindow: String,
        @Query("page") page: Int = 1,
        @Query("language") language: String = "en-US",
    ): NetworkResponse<GetMediaContentsResponse>

    /**
     * Get a list of movies ordered by popularity.
     *
     * @param page
     * @param language
     *
     * @return [GetMediaContentsResponse]
     */
    @GET("movie/popular")
    suspend fun fetchPopularMovies(
        @Query("page") page: Int = 1,
        @Query("language") language: String = "en-US",
    ): NetworkResponse<GetMoviesResponse>

    /**
     * Get a list of TV shows ordered by popularity.
     *
     * @param page
     * @param language
     *
     * @return [GetMediaContentsResponse]
     */
    @GET("tv/popular")
    suspend fun fetchPopularTvShows(
        @Query("page") page: Int = 1,
        @Query("language") language: String = "en-US",
    ): NetworkResponse<GetTvShowsResponse>

    /**
     * Get a list of movies ordered by rating.
     *
     * @param page
     * @param language
     *
     * @return [GetMoviesResponse]
     */
    @GET("movie/top_rated")
    suspend fun fetchTopRatedMovies(
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
    suspend fun fetchNowPlayingMovies(
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
    suspend fun fetchUpcomingMovies(
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
    suspend fun fetchSearchMoviesByQuery(
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

