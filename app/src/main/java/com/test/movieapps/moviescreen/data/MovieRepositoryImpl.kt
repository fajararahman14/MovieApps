package com.test.movieapps.moviescreen.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.test.movieapps.Resource
import com.test.movieapps.moviescreen.data.dto.GenreItem
import com.test.movieapps.moviescreen.data.dto.ListMovieResponseDto
import com.test.movieapps.moviescreen.data.dto.MovieItem
import com.test.movieapps.moviescreen.data.paging.PagingSourceFactory
import com.test.movieapps.moviescreen.domain.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class MovieRepositoryImpl(
    private val movieApiInterface: MovieApiInterface
) : MovieRepository {
    override suspend fun getGenre(): Flow<Resource<GenreItem>> {
        TODO("Not yet implemented")
    }

    override suspend fun getListMovie(
        genreId: Int,
        page: Int
    ): Flow<Resource<ListMovieResponseDto>> {
        return flow {
            emit(Resource.Loading("Loading"))
            try {
                val response = movieApiInterface.getMovie(genreId, page)
                if (response.isSuccessful) {
                    val responseData = response.body()
                    if (responseData != null) {
                        // Filter out null values from results
                        val validResults = responseData.results?.filterNotNull() ?: emptyList()
                        val updatedResponse = responseData.copy(results = validResults)
                        emit(Resource.Success(updatedResponse))
                    } else {
                        emit(Resource.Error("Response body is null"))
                    }
                } else {
                    emit(Resource.Error("Error: ${response.code()} - ${response.message()}"))
                }
            } catch (e: Exception) {
                when (e) {
                    is SocketTimeoutException -> {
                        emit(Resource.Error("Connection timeout, please try again"))
                    }
                    is UnknownHostException -> {
                        emit(Resource.Error("No internet connection"))
                    }
                    is ConnectException -> {
                        emit(Resource.Error("Connection error, please try again"))
                    }
                    else -> {
                        emit(Resource.Error(e.message ?: "An unknown error occurred"))
                    }
                }
            }
        }.flowOn(Dispatchers.IO)
    }


    override suspend fun pagingGetListMovie(genreId: Int): Flow<PagingData<MovieItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { PagingSourceFactory(movieApiInterface, genreId) }
        ).flow
    }
}