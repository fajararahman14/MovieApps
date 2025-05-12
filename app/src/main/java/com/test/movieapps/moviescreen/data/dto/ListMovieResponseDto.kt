package com.test.movieapps.moviescreen.data.dto

data class ListMovieResponseDto(
	val page: Int? = null,
	val totalPages: Int? = null,
	val results: List<MovieItem?>? = null,
	val totalResults: Int? = null
)

