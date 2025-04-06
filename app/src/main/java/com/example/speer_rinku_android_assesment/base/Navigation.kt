package com.example.speer_rinku_android_assesment.base

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.speer_rinku_android_assesment.compose.FollowerList
import com.example.speer_rinku_android_assesment.compose.FollowingList
import com.example.speer_rinku_android_assesment.compose.GitHubUserSearch
import com.example.speer_rinku_android_assesment.viewModel.GitHubViewModel


object Route {
    const val USER = "user"
    const val FOLLOWING = "following"
    const val FOLLOWER = "follower"
}


sealed class NavRoute(val route: String) {
    object USER : NavRoute(Route.USER)
    object Following : NavRoute(Route.FOLLOWING)
    object Follower : NavRoute(Route.FOLLOWER)
}

@Composable
fun RouteNavigation(gitHubViewModel: GitHubViewModel) {

    val remNavController = rememberNavController()

    NavHost(remNavController, startDestination = NavRoute.USER.route) {
        composable(NavRoute.USER.route) {
            GitHubUserSearch(gitHubViewModel, remNavController)
        }
        composable("${NavRoute.Follower.route}/{userName}", arguments = listOf(
            navArgument("userName") { type = NavType.StringType }
        )) {backStackEntry->
            val userName = backStackEntry.arguments?.getString("userName")
            FollowerList(gitHubViewModel, remNavController,userName)
        }
        composable("${NavRoute.Following.route}/{userName}", arguments = listOf(
            navArgument("userName") { type = NavType.StringType }
        )) {backStackEntry ->
            val userName = backStackEntry.arguments?.getString("userName")
            FollowingList(gitHubViewModel, remNavController,userName)
        }

    }

}