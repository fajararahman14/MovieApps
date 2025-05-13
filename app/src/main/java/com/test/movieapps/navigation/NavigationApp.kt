package com.test.movieapps.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.test.movieapps.detailmoviescreen.presentation.DetailMovieScreen
import com.test.movieapps.moviescreen.presentation.MovieScreen

@Composable
fun NavigationApp(navController: NavHostController) {
    val appNavController = rememberNavController()

    NavHost(
        navController = appNavController,
        startDestination = NavigationRoutes.Home.route
    ) {
        composable(NavigationRoutes.Home.route) {
            MovieScreen(
                modifier = Modifier,
                navController = appNavController
            )
        }
        composable(NavigationRoutes.Detail.route){
            DetailMovieScreen(navController = appNavController)
        }
    }
}