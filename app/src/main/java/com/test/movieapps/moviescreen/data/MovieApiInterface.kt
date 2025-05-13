package com.test.movieapps.moviescreen.data

import com.test.movieapps.moviescreen.data.dto.GenreResponseDto
import com.test.movieapps.moviescreen.data.dto.ListMovieResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApiInterface {

    @GET("genre/movie/list")
    suspend fun getGenre(): Response<GenreResponseDto>

    @GET("discover/movie")
    suspend fun getMovie(
        @Query("with_genres") genreId: String,
        @Query("page") page: Int
    ): Response<ListMovieResponseDto>


}