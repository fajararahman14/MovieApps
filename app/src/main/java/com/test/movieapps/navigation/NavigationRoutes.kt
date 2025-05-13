package com.test.movieapps.navigation

sealed class NavigationRoutes(
    val route: String
) {
   data object Home : NavigationRoutes("home")
   data object Detail : NavigationRoutes("detail")
}