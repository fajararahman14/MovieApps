package com.test.movieapps.detailmoviescreen.domain

import com.test.movieapps.Resource
import kotlinx.coroutines.flow.Flow

interface DetailRepository {
    suspend fun getGenre() : Flow<Resource<Unit>>
    suspend fun getTrailer() : Flow<Resource<Unit>>
}