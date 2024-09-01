package com.dcs.data.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.dcs.data.model.TvShowType
import com.dcs.data.remote.datasource.TvShowRemoteDataSource
import com.dcs.domain.model.MediaPolymorphic
import timber.log.Timber

class TvShowPagingSource(
    private val tvShowType: TvShowType,
    private val tvShowRemoteDataSource: TvShowRemoteDataSource,
    private val language: String = "en-US",
) : PagingSource<Int, MediaPolymorphic.TvShow>() {

    override fun getRefreshKey(state: PagingState<Int, MediaPolymorphic.TvShow>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MediaPolymorphic.TvShow> {
        val page = params.key ?: START_PAGE_INDEX

        return try {
            val (contents, totalPages) = tvShowRemoteDataSource.getTvShows(
                tvShowType = tvShowType,
                page = page,
                language = language
            )
                .getOrThrow()
                .let {
                    it.results to it.totalPages
                }

            LoadResult.Page(
                data = contents.map { it.toEntity() as MediaPolymorphic.TvShow },
                prevKey = if (page != START_PAGE_INDEX) page - 1 else null,
                nextKey = if (page < totalPages) page + 1 else null
            )
        } catch (e: Exception) {
            Timber.e(e)
            LoadResult.Error(e)
        }
    }

    companion object {
        private const val START_PAGE_INDEX = 1
    }
}

