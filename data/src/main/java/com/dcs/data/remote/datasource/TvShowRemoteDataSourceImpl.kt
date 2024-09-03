package com.dcs.data.remote.datasource

import com.dcs.data.model.TvShowType
import com.dcs.data.remote.model.GetTvShowDetailResponse
import com.dcs.data.remote.model.GetTvShowsResponse
import com.dcs.data.remote.network.NetworkResponse
import com.dcs.data.remote.service.MovieService
import com.dcs.domain.model.MediaContentId
import javax.inject.Inject

class TvShowRemoteDataSourceImpl @Inject constructor(
    private val movieService: MovieService,
) : TvShowRemoteDataSource {
    override suspend fun getTvShows(
        tvShowType: TvShowType,
        page: Int,
        language: String,
    ): Result<GetTvShowsResponse> {
        return when (tvShowType) {
            is TvShowType.Popular -> {
                getPopularTvShows(page, language)
            }
        }.asResult {
            it.data as GetTvShowsResponse
        }
    }

    override suspend fun getTvShowDetailById(
        mediaContentId: MediaContentId,
        appendToResponse: String,
        language: String,
    ): Result<GetTvShowDetailResponse> =
        movieService.fetchTvShowDetailById(
            seriesId = mediaContentId.value,
            appendToResponse = appendToResponse,
            language = language
        ).asResult {
            it.data as GetTvShowDetailResponse
        }

    private suspend fun getPopularTvShows(
        page: Int,
        language: String,
    ): NetworkResponse<GetTvShowsResponse> =
        movieService.fetchPopularTvShows(
            page = page,
            language = language
        )
}
