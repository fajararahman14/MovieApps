package com.test.movieapps.moviescreen.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.test.movieapps.Resource
import com.test.movieapps.moviescreen.data.dto.GenreItem
import com.test.movieapps.moviescreen.data.dto.MovieItem
import com.test.movieapps.moviescreen.domain.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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


    private val _pagerFlow = MutableStateFlow<PagingData<MovieItem>>(PagingData.empty())
    val pagerFlow: StateFlow<PagingData<MovieItem>> = _pagerFlow


    init {
        getGenre()
    }
    suspend fun getPageMovie(genreId: String) {
        movieRepository.pagingGetListMovie(genreId).cachedIn(viewModelScope).collectLatest { data ->
            _pagerFlow.value = data
        }
    }

    private fun getGenre() {
        viewModelScope.launch {
            movieRepository.getGenre().collectLatest { resource ->
                when (resource) {
                    is Resource.Error -> {
                        // Handle the error and update the UI state with the error message
                        _uiState.value = MovieUiState(errorMessage = resource.message ?: "Unknown error")
                    }
                    is Resource.Loading -> {
                        // Set the loading state to true
                        _uiState.value = MovieUiState(isLoading = true)
                    }
                    is Resource.Success -> {
                        val data = resource.data
                        _uiState.value = MovieUiState(listGenre = data as List<GenreItem?>?)
                    }
                }
            }
        }
    }
}