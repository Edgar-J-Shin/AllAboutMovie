package com.dcs.data.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.dcs.data.model.MediaContentType
import com.dcs.data.remote.datasource.MovieRemoteDataSource
import com.dcs.domain.model.MediaPolymorphic
import timber.log.Timber

class MediaContentPagingSource(
    private val mediaContentType: MediaContentType,
    private val movieRemoteDataSource: MovieRemoteDataSource,
    private val language: String = "en-US",
) : PagingSource<Int, MediaPolymorphic>() {

    override fun getRefreshKey(state: PagingState<Int, MediaPolymorphic>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MediaPolymorphic> {
        val page = params.key ?: START_PAGE_INDEX

        return try {
            val (contents, totalPages: Int) = movieRemoteDataSource.getMediaContents(
                mediaContentType = mediaContentType,
                page = page,
                language = language
            )
                .getOrThrow()
                .let {
                    it.results to it.totalPages
                }

            LoadResult.Page(
                data = contents.map { it.toEntity() },
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

