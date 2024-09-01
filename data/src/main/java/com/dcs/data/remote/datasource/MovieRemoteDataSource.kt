package com.dcs.data.remote.datasource

import com.dcs.data.model.MediaContentType
import com.dcs.data.model.MovieType
import com.dcs.data.remote.model.GetMediaContentsResponse
import com.dcs.data.remote.model.GetMovieDetailResponse
import com.dcs.data.remote.model.GetMoviesResponse
import com.dcs.domain.model.MediaContentId

interface MovieRemoteDataSource {

    suspend fun getMediaContents(
        mediaContentType: MediaContentType,
        page: Int,
        language: String,
    ): Result<GetMediaContentsResponse>

    suspend fun getMovies(
        movieType: MovieType,
        page: Int,
        language: String,
    ): Result<GetMoviesResponse>

    suspend fun getMovieDetailById(
        mediaContentId: MediaContentId,
        appendToResponse: String,
        language: String,
    ): Result<GetMovieDetailResponse>
}
