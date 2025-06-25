package com.android.rickandmorty.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.android.rickandmorty.data.model.Episode
import com.android.rickandmorty.ui.composables.LoginScreen
import com.android.rickandmorty.ui.composables.RegisterScreen
import com.android.rickandmorty.ui.view.DetailEpisodeScreen
import com.android.rickandmorty.ui.view.ExploreScreen

sealed class Screen(val route: String) {
    data object Register : Screen("register")
    data object Login : Screen("login")
    data object Home : Screen("home")
    data object Detail : Screen("detail")
}

@Composable
fun AppNavGraph(modifier: Modifier, navController: NavHostController, isAuthenticated: Boolean) {
    NavHost(
        navController = navController,
        startDestination = if (isAuthenticated) Screen.Login.route else Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            ExploreScreen(
                modifier = modifier,
                onEpisodeClick = {
                    navController.navigate(Screen.Detail.route)
                }
            )
        }
        composable(Screen.Detail.route) {
            DetailEpisodeScreen(modifier, episode = Episode(1, "Pilot", "December 2, 2013", "S01E01"))
        }
        composable(Screen.Register.route) {
            RegisterScreen({
                navController.navigate(Screen.Home.route) {
                    popUpTo(Screen.Login.route) {
                        inclusive = true
                    }
                }
            },{
                navController.popBackStack()
            },{_,_,_ ->})
        }
        composable(Screen.Login.route) {
            LoginScreen(modifier, {
                navController.navigate(Screen.Home.route){
                    popUpTo(Screen.Login.route){
                        inclusive = true
                    }
                }
            },{
                navController.navigate(Screen.Register.route)
            })
        }
    }
}