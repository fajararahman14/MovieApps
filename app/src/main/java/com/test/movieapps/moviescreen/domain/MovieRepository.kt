package com.test.movieapps.moviescreen.domain

import androidx.paging.PagingData
import com.test.movieapps.Resource
import com.test.movieapps.moviescreen.data.dto.GenreItem
import com.test.movieapps.moviescreen.data.dto.ListMovieResponseDto
import com.test.movieapps.moviescreen.data.dto.MovieItem
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    suspend fun getGenre(): Flow<Resource<GenreItem>>
    suspend fun getListMovie(genreId: Int, page: Int): Flow<Resource<ListMovieResponseDto>>
    suspend fun pagingGetListMovie(genreId: Int): Flow<PagingData<MovieItem>>
}