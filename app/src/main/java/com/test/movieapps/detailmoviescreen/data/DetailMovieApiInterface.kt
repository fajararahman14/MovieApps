package com.test.movieapps.detailmoviescreen.data

import com.test.movieapps.detailmoviescreen.data.dto.DetailMovieResponseDto
import com.test.movieapps.detailmoviescreen.data.dto.DetailMovieReviewResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface DetailMovieApiInterface {
    @GET("/movie/{movie_id}")
    suspend fun getDetailMovie(@Query("movie_id") movieId: Int): DetailMovieResponseDto

    @GET("/movie/{movie_id}/reviews")
    suspend fun getDetailMovieReview(@Query("movie_id") movieId: Int): DetailMovieReviewResponseDto

    @GET(("/movie/{movie_id}/videos"))
    suspend fun getDetailMovieVideo(@Query("movie_id") movieId: Int): DetailMovieResponseDto
}