package com.dcs.data.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.dcs.data.Trend
import com.dcs.data.model.mapper.toEntity
import com.dcs.data.remote.datasource.MovieRemoteDataSource
import com.dcs.domain.model.MovieEntity

class MoviePagingSource(
    private val trend: Trend,
    private val movieRemoteDataSource: MovieRemoteDataSource,
    private val language: String = "en-US",
) : PagingSource<Int, MovieEntity>() {

    override fun getRefreshKey(state: PagingState<Int, MovieEntity>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieEntity> {
        val page = params.key ?: START_PAGE_INDEX

        return try {
            val result = when (trend) {
                is Trend.Trending -> {
                    movieRemoteDataSource.getMoviesByTrending(trend.timeWindow, page, language)
                }

                is Trend.Popular -> {
                    movieRemoteDataSource.getMoviesByPopular(trend.mediaType, page, language)
                }

                Trend.TopRated -> {
                    movieRemoteDataSource.getMoviesByTopRated(page, language)
                }

                Trend.Upcoming -> {
                    movieRemoteDataSource.getMoviesByUpcoming(page, language)
                }
                is Trend.Search -> {
                    movieRemoteDataSource.getSearchMulti(trend.query, page, language)
                }
            }

            val (movies, totalPages) = result.getOrThrow().let { it.results to it.totalPages }

            LoadResult.Page(
                data = movies.map { it.toEntity() }.distinct(),
                prevKey = if (page != START_PAGE_INDEX) page - 1 else null,
                nextKey = if (page < totalPages) page + 1 else null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    companion object {
        private const val START_PAGE_INDEX = 1
    }
}

