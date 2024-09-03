package com.dcs.data.remote.datasource

import com.dcs.data.model.TvShowType
import com.dcs.data.remote.model.GetTvShowDetailResponse
import com.dcs.data.remote.model.GetTvShowsResponse
import com.dcs.domain.model.MediaContentId

interface TvShowRemoteDataSource {

    suspend fun getTvShows(
        tvShowType: TvShowType,
        page: Int,
        language: String,
    ): Result<GetTvShowsResponse>

    suspend fun getTvShowDetailById(
        mediaContentId: MediaContentId,
        appendToResponse: String,
        language: String,
    ): Result<GetTvShowDetailResponse>
}
