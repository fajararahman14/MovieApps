package com.test.movieapps.moviescreen.domain

import androidx.paging.PagingData
import com.test.movieapps.Resource
import com.test.movieapps.moviescreen.data.dto.GenreResponseDto
import com.test.movieapps.moviescreen.data.dto.MovieItem
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    suspend fun getGenre(): Flow<Resource<GenreResponseDto>>
    suspend fun pagingGetListMovie(genreId: String): Flow<PagingData<MovieItem>>
}