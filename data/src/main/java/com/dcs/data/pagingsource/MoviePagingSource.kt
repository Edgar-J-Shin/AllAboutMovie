package com.dcs.data.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.dcs.data.MovieRanking
import com.dcs.data.model.mapper.toEntity
import com.dcs.data.remote.datasource.MovieRemoteDataSource
import com.dcs.domain.model.MovieEntity
import retrofit2.HttpException
import java.io.IOException

class MoviePagingSource(
    private val movieRanking: MovieRanking,
    private val movieRemoteDataSource: MovieRemoteDataSource,
    private val pageSize: Int,
    private val language: String = "en-US"
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

            val result = when (movieRanking) {
                is MovieRanking.Trending -> movieRemoteDataSource.getMoviesByTrending(movieRanking.timeWindow, page + 1, language)
                is MovieRanking.TopRated -> movieRemoteDataSource.getMoviesByTopRated(page + 1, language)
            }

            val (movies, totalPages) = result.getOrThrow().run { results to totalPages }

            val hasPrevPage = page != startPageIndex
            val hasNextPage = movies.isNotEmpty() && !(page == startPageIndex && totalPages < pageSize)

            LoadResult.Page(
                data = movies.map { it.toEntity() }.distinct(),
                prevKey = if (hasPrevPage) page - 1 else null,
                nextKey = if (hasNextPage) page + 1 else null
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }
}