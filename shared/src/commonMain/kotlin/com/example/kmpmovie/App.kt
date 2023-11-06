package com.example.kmpmovie

import androidx.compose.foundation.layout.Column
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import com.example.kmpmovie.navigation.Navigation
import com.example.kmpmovie.navigation.NavigationScreen
import com.example.kmpmovie.navigation.currentRoute
import com.example.kmpmovie.ui.AppViewModel
import com.example.kmpmovie.ui.component.AppBarWithArrow
import com.example.kmpmovie.utils.AppString
import kotlinx.coroutines.ExperimentalCoroutinesApi
import moe.tlaster.precompose.PreComposeApp
import moe.tlaster.precompose.navigation.BackHandler
import moe.tlaster.precompose.navigation.NavOptions
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.rememberNavigator
import theme.FloatingActionBackground
import ui.component.ProgressIndicator
import ui.component.SearchBar
import ui.component.SearchUI
import utils.pagingLoadingState

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
internal fun App(viewModel: AppViewModel = AppViewModel()) {
   PreComposeApp {
       val navigator = rememberNavigator()
       val isAppBarVisible = remember { mutableStateOf(true) }
       val searchProgressBar = remember { mutableStateOf(false) }

       BackHandler(isAppBarVisible.value.not()) {
           isAppBarVisible.value = true
       }

       MaterialTheme {
           Scaffold(topBar = {
               if (isAppBarVisible.value.not()) {
                   SearchBar(viewModel) {
                       isAppBarVisible.value = true
                   }

               } else {
                   AppBarWithArrow(
                       AppString.APP_TITLE, isBackEnable = isBackButtonEnable(navigator)
                   ) {
                       navigator.goBack()
                   }
               }
           }, floatingActionButton = {
               when (currentRoute(navigator)) {
                   NavigationScreen.Home.route, NavigationScreen.Popular.route, NavigationScreen.TopRelated.route, NavigationScreen.UpComing.route -> {
                       FloatingActionButton(
                           onClick = {
                               isAppBarVisible.value = false
                           }, backgroundColor = FloatingActionBackground
                       ) {
                           Icon(Icons.Filled.Search, "", tint = Color.White)
                       }
                   }
               }
           }, bottomBar = {
               when (currentRoute(navigator)) {
                   NavigationScreen.Home.route, NavigationScreen.Popular.route, NavigationScreen.TopRelated.route, NavigationScreen.UpComing.route -> {
                       BottomNavigationUI(navigator)
                   }
               }
           }) {
               Navigation(navigator)
               if (currentRoute(navigator) !== NavigationScreen.MovieDetail.route) {
                   Column {
                       if (isAppBarVisible.value.not()) {
                           SearchUI(navigator, viewModel.searchData) {
                               isAppBarVisible.value = true
                           }
                           ProgressIndicator(searchProgressBar.value)
                       }
                       viewModel.searchData.pagingLoadingState {
                           searchProgressBar.value = it
                       }
                   }
               }
           }
       }
   }
}

@Composable
fun BottomNavigationUI(navigator: Navigator) {
    BottomNavigation {
        val items = listOf(
            NavigationScreen.HomeNav,
            NavigationScreen.PopularNav,
            NavigationScreen.TopRatedNav,
            NavigationScreen.UpcomingNav,
        )
        items.forEach {
            BottomNavigationItem(label = { Text(text = it.title) },
                selected = it.route == currentRoute(navigator),
                icon = it.navIcon,
                onClick = {
                    navigator.navigate(
                        it.route,
                        NavOptions(
                            launchSingleTop = true,
                        ),
                    )
                })
        }
    }
}

@Composable
fun isBackButtonEnable(navigator: Navigator): Boolean {
    return when (currentRoute(navigator)) {
        NavigationScreen.Home.route, NavigationScreen.Popular.route, NavigationScreen.TopRelated.route, NavigationScreen.UpComing.route -> {
            false
        }

        else -> {
            true
        }
    }
}