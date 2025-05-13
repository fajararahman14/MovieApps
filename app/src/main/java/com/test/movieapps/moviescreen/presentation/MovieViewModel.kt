package com.test.movieapps.moviescreen.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.movieapps.Resource
import com.test.movieapps.moviescreen.domain.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(MovieUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getListMovie(0,1)
    }


    fun getListMovie(genreId: Int, page: Int) {
        viewModelScope.launch {
            movieRepository.getListMovie(genreId, page).collectLatest {

                val listMovie = it.data?.results
                when (it) {
                    is Resource.Loading -> {
                        _uiState.value = _uiState.value.copy(isLoading = true)
                    }

                    is Resource.Success -> {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            listMovie = listMovie ?: emptyList()
                        )
                    }

                    is Resource.Error -> {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            errorMessage = it.message ?: "unknown error"
                        )
                    }
                }
            }
        }
    }
}