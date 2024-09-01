package com.dcs.data.remote.datasource

import com.dcs.data.model.TvShowType
import com.dcs.data.remote.model.GetTvShowsResponse

interface TvShowRemoteDataSource {

    suspend fun getTvShows(
        tvShowType: TvShowType,
        page: Int,
        language: String,
    ): Result<GetTvShowsResponse>
}
