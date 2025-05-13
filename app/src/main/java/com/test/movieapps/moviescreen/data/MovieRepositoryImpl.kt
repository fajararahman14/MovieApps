package com.test.movieapps.moviescreen.data

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.test.movieapps.Resource
import com.test.movieapps.moviescreen.data.dto.GenreResponseDto
import com.test.movieapps.moviescreen.data.dto.MovieItem
import com.test.movieapps.moviescreen.data.paging.PagingSourceFactory
import com.test.movieapps.moviescreen.domain.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class MovieRepositoryImpl(
    private val movieApiInterface: MovieApiInterface
) : MovieRepository {
    override suspend fun getGenre(): Flow<Resource<GenreResponseDto>> {
        return flow {
            emit(Resource.Loading("Loading Genre"))
            try {
                val response = movieApiInterface.getGenre()
                if (response.isSuccessful) {
                    val data = response.body() // This should return GenreResponseDto, not List<GenreItem>
                    if (data != null) {
                        emit(Resource.Success(data))  // Emitting the GenreResponseDto
                    } else {
                        emit(Resource.Error("No data found"))
                    }
                } else {
                    emit(Resource.Error("Error: ${response.message()}"))
                }
            } catch (e: SocketTimeoutException) {
                emit(Resource.Error("Timeout"))
            } catch (e: UnknownHostException) {
                emit(Resource.Error("No Connection"))
            } catch (e: Exception) {
                emit(Resource.Error("Unexpected error: ${e.localizedMessage}"))
            }
        }.flowOn(Dispatchers.IO)
    }


    override suspend fun pagingGetListMovie(genreId: String): Flow<PagingData<MovieItem>> {
        Log.d("pagingGetListMovie", "Ini TEST")
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),

            pagingSourceFactory = { PagingSourceFactory(movieApiInterface, genreId.toString()) }
        ).flow
    }
}