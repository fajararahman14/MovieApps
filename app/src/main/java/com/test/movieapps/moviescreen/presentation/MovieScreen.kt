package com.test.movieapps.moviescreen.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel


@Composable
fun MovieScreen(modifier: Modifier = Modifier) {

    val viewModel = hiltViewModel<MovieViewModel>()
    val state by viewModel.uiState.collectAsState()
    val listMovie = state.listMovie

    LaunchedEffect("getTheData") {
        viewModel.getListMovie(0, 1)
    }
    Text("Ini Movie Screen")
    LazyColumn(modifier = modifier.fillMaxSize()) {
        items(listMovie ?: emptyList()) { movie ->
            // Handle nullable movie properly
            movie?.let {
                Text(text = it.title ?: "No title available")
                // Add more UI components for movie details
            }
        }
    }

}