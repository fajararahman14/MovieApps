package com.test.movieapps.detailmoviescreen.data

import com.test.movieapps.Resource
import com.test.movieapps.detailmoviescreen.domain.DetailRepository
import kotlinx.coroutines.flow.Flow


class DetailRepositoryImpl : DetailRepository{
    override suspend fun getGenre(): Flow<Resource<Unit>> {
        TODO("Not yet implemented")
    }

    override suspend fun getTrailer(): Flow<Resource<Unit>> {
        TODO("Not yet implemented")
    }
}