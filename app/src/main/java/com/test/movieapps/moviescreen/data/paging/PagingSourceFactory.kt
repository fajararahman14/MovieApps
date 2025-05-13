package com.test.movieapps.moviescreen.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingSourceFactory
import androidx.paging.PagingState
import com.test.movieapps.Resource
import com.test.movieapps.moviescreen.data.MovieApiInterface
import com.test.movieapps.moviescreen.data.dto.ListMovieResponseDto
import com.test.movieapps.moviescreen.data.dto.MovieItem
import com.test.movieapps.moviescreen.domain.MovieRepository

class PagingSourceFactory(
    private val movieApiInterface: MovieApiInterface,
    private val genreId: Int
) : PagingSource<Int, MovieItem>() {
    override fun getRefreshKey(state: PagingState<Int, MovieItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val closestPageToAnchor = state.closestPageToPosition(anchorPosition)
            closestPageToAnchor?.prevKey?.plus(1) ?: closestPageToAnchor?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieItem> {
        return try {
            val page = params.key ?: 1
            val response = movieApiInterface.getMovie(genreId, page)
            val dataList = response.body()?.results?.filterNotNull() ?: emptyList()
            LoadResult.Page(
                data = dataList,
                prevKey = if (page > 1) page - 1 else null,
                nextKey = if (dataList.isNotEmpty()) page + 1 else null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }

    }

}