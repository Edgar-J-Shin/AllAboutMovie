package com.dcs.data.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.dcs.data.model.mapper.toEntity
import com.dcs.data.remote.datasource.PersonRemoteDataSource
import com.dcs.domain.model.Person

class PopularPersonPagingSource(
    private val remote: PersonRemoteDataSource,
    private val pageSize: Int,
    private val language: String = "en-US",
    private val startPageIndex: Int = 0,
) : PagingSource<Int, Person>() {

    override fun getRefreshKey(state: PagingState<Int, Person>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Person> {
        val page = params.key ?: startPageIndex

        return runCatching {
            val (popular, totalPage) = remote
                .getPopularPeople(page + 1, language)
                .getOrThrow()
                .let { it.results to it.totalPages }

            val hasPrevPage = page != startPageIndex
            val hasNextPage =
                popular.isNotEmpty() && !(page == startPageIndex && totalPage < pageSize)

            LoadResult.Page(
                data = popular.map { it.toEntity() },
                prevKey = if (hasPrevPage) page - 1 else null,
                nextKey = if (hasNextPage) page + 1 else null
            )
        }.getOrElse { e ->
            LoadResult.Error(e)
        }
    }
}
