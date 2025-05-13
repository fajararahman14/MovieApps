package com.test.movieapps.moviescreen.presentation

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.collectAsState

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.paging.LoadState
import com.test.movieapps.moviescreen.data.dto.GenreItem
import com.test.movieapps.navigation.NavigationRoutes


@Composable
fun MovieScreen(modifier: Modifier = Modifier, navController: NavController ) {
    val viewModel = hiltViewModel<MovieViewModel>()
    val lazyMovieItems = viewModel.pagerFlow.collectAsLazyPagingItems()
    val state by viewModel.uiState.collectAsState()

    LaunchedEffect("getLazyMovieItem") {
        viewModel.getPageMovie("") // Ensure page 0 is loaded initially
    }

    LazyColumn(modifier = modifier.fillMaxSize()) {
        // Handling Genre Row (ensure it's not null)
        item {
            CategoryRow(
                categories = state.listGenre ?: emptyList() // Provide an empty list if null
            )
        }

        // Lazy List of Movie Items
        items(lazyMovieItems.itemCount) { index ->
            // Only process if the movie is not null
            lazyMovieItems[index]?.let { movie ->
                ItemMovie(
                    itemEntity = movie,
                    onClick = {
                        navController.navigate(NavigationRoutes.Detail.route + "/${movie.id}")
                    }
                )
            }
        }

        // Handling loading or error states for lazy paging items
        lazyMovieItems.apply {
            when {
                loadState.refresh is LoadState.Loading -> {
                    item {LoadingNextPageItem() }
                }
                lazyMovieItems.loadState.append is LoadState.Loading -> {
                    item {LoadingNextPageItem() }

                }
                lazyMovieItems.loadState.refresh is LoadState.Error -> {
                    val error = lazyMovieItems.loadState.refresh as LoadState.Error
                    item { ErrorMessage(
                        message = error.error.localizedMessage ?: "Unknown error",
                        onClickRetry = {retry()},
                    )}

                }
                lazyMovieItems.loadState.append is LoadState.Error -> {
                    val error = lazyMovieItems.loadState.append as LoadState.Error
                    item {
                        ErrorMessage(
                            message = error.error.localizedMessage ?: "Unknown error",
                            onClickRetry = {retry()},
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun LoadingNextPageItem(modifier: Modifier = Modifier) {
    CircularProgressIndicator(
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp)
            .wrapContentWidth(Alignment.CenterHorizontally)
    )
}

@Composable
fun ErrorMessage(
    message: String,
    modifier: Modifier = Modifier,
    onClickRetry: () -> Unit
) {
    Row(
        modifier = modifier.padding(10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = message,
            color = MaterialTheme.colorScheme.error,
            modifier = Modifier.weight(1f),
            maxLines = 2
        )
        OutlinedButton (onClick = onClickRetry) {
            Text(text = "Retry")
        }
    }
}
@Composable
fun CategoryRow(categories: List<GenreItem?>) {
    LazyRow(
        modifier = Modifier.padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(categories) { category ->
            category?.let {
                CategoryChip(it) // Handle null category here
            }
        }
    }
}

@Composable
fun CategoryChip(category: GenreItem) {
    // Chip with category name
    Button(
        onClick = { },
        modifier = Modifier.padding(4.dp),
        shape = MaterialTheme.shapes.small,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = Color.White
        )
    ) {
        Text(
            text = category.name ?: "Unknown", // Handle null category name
            style = MaterialTheme.typography.bodyMedium,
            color = Color.White
        )
    }
}

