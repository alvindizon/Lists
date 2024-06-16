package com.alvindizon.lists.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.alvindizon.lists.ui.ListDetailsScreen
import com.alvindizon.lists.ui.MyListsScreen


@Composable
fun AppNavGraph(navController: NavHostController = rememberNavController()) {
    NavHost(navController, startDestination = "MyLists") {
        composable(route = "MyLists") {
            MyListsScreen(onListClick = { navController.navigate(route = "ListDetails/${it}") })
        }
        composable(route = "ListDetails/{listId}", arguments = listOf(navArgument("listId") { type = NavType.IntType})) {
            ListDetailsScreen(
                onNavigationIconClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}

