package com.test.movieapps.moviescreen.presentation

import com.test.movieapps.moviescreen.data.dto.MovieItem

data class MovieUiState(
    val id : String = "",
    val title : String = "",
    val isLoading : Boolean = false,
    val errorMessage : String = "",

    val listMovie : List<MovieItem?>? = emptyList(),

)