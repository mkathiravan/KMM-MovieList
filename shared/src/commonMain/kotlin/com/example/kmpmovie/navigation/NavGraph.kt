package com.example.kmpmovie.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.example.kmpmovie.ui.detail.MovieDetail
import com.example.kmpmovie.ui.home.HomeScreen
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.path
import ui.popular.Popular
import ui.toprated.TopRated
import ui.upcoming.Upcoming

@Composable
fun Navigation(navigator: Navigator) {
    NavHost(
        navigator = navigator,
        initialRoute = NavigationScreen.Home.route,
    ) {
        scene(route = NavigationScreen.Home.route) {
            HomeScreen(navigator)
        }
        scene(route = NavigationScreen.Popular.route) {
            Popular(navigator)
        }
        scene(route = NavigationScreen.TopRelated.route) {
            TopRated(navigator)
        }
        scene(route = NavigationScreen.UpComing.route) {
            Upcoming(navigator)
        }
        scene(route = NavigationScreen.MovieDetail.route.plus(NavigationScreen.MovieDetail.objectPath)) { backStackEntry ->
            val id: Int? = backStackEntry.path<Int>(NavigationScreen.MovieDetail.objectName)
            id?.let {
                MovieDetail(navigator, it)
            }
        }
    }
}

@Composable
fun currentRoute(navigator: Navigator): String? {
    return navigator.currentEntry.collectAsState(null).value?.route?.route

}