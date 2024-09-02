package com.dcs.data.remote.datasource

import com.dcs.data.model.TvShowType
import com.dcs.data.remote.model.GetTvShowsResponse
import com.dcs.data.remote.network.NetworkResponse
import com.dcs.data.remote.service.MovieService
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

    private suspend fun getPopularTvShows(
        page: Int,
        language: String,
    ): NetworkResponse<GetTvShowsResponse> =
        movieService.fetchPopularTvShows(
            page = page,
            language = language
        )
}
