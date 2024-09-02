package com.dcs.data.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.dcs.data.model.mapper.toEntity
import com.dcs.data.remote.datasource.PersonRemoteDataSource
import com.dcs.domain.model.Person
import timber.log.Timber

class PopularPeoplePagingSource(
    private val remote: PersonRemoteDataSource,
    private val language: String = "en-US",
) : PagingSource<Int, Person>() {

    override fun getRefreshKey(state: PagingState<Int, Person>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Person> {
        return try {
            val page = params.key ?: START_PAGE_INDEX
            val (popular, totalPages) = remote
                .getPopularPeople(page, language)
                .getOrThrow()
                .let { it.results to it.totalPages }

            val prevKey = if (page == START_PAGE_INDEX) null else page - 1
            val nextKey = if (page < totalPages) page + 1 else null

            LoadResult.Page(
                data = popular.map { it.toEntity() },
                prevKey = prevKey,
                nextKey = nextKey,
            )
        } catch (e: Exception) {
            Timber.tag(TAG).e(e, "load error")
            LoadResult.Error(e)
        }
    }

    companion object {
        private val TAG = PopularPeoplePagingSource::class.java.simpleName

        private const val START_PAGE_INDEX = 1
    }
}
