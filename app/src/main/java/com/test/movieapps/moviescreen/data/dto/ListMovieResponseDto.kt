package com.test.movieapps.moviescreen.data.dto

import com.google.gson.annotations.SerializedName

data class ListMovieResponseDto(
	val page: Int? = null,
	@SerializedName("total_pages")
	val totalPages: Int? = null,
	val results: List<MovieItem?>? = null,
	@SerializedName("total_results")
	val totalResults: Int? = null
)

