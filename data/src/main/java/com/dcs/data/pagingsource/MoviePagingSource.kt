package com.dcs.data.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.dcs.data.model.mapper.toEntity
import com.dcs.data.remote.datasource.MovieRemoteDataSource
import com.dcs.data.remote.model.MoviesResponse
import com.dcs.domain.model.MovieEntity
import retrofit2.HttpException
import java.io.IOException

class MoviePagingSource(
    private val movieRemoteDataSource: MovieRemoteDataSource,
    private val pageSize: Int
) : PagingSource<Int, MovieEntity>() {

    private val startPageIndex = 0
    override fun getRefreshKey(state: PagingState<Int, MovieEntity>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieEntity> {
        val page = params.key ?: startPageIndex

        return try {

            val (movies, totalPages) = movieRemoteDataSource.getMoviesByTopRated(page)
                .handleResponse { it.data as MoviesResponse }
                .getOrThrow()
                .run { results to totalPages }

            val hasPrevPage = page != startPageIndex
            val hasNextPage = movies.isNotEmpty() && !(page == startPageIndex && totalPages < pageSize)

            LoadResult.Page(
                data = movies.map { it.toEntity() }.distinct(),
                prevKey = if (hasPrevPage) page - 1 else null,
                nextKey = if (hasNextPage) page + 1 else null
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }
}