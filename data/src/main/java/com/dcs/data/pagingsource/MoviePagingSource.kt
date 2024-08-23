package com.dcs.data.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.dcs.data.model.MovieType
import com.dcs.data.model.mapper.toEntity
import com.dcs.data.remote.datasource.MovieRemoteDataSource
import com.dcs.domain.model.Movie

class MoviePagingSource(
    private val movieType: MovieType,
    private val movieRemoteDataSource: MovieRemoteDataSource,
    private val language: String = "en-US",
) : PagingSource<Int, Movie>() {

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val page = params.key ?: START_PAGE_INDEX

        return try {
            val (movies, totalPages) = movieRemoteDataSource.getMovies(
                movieType = movieType,
                page = page,
                language = language
            )
                .getOrThrow()
                .let {
                    it.results to it.totalPages
                }

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

