package com.alvindizon.lists.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.alvindizon.lists.ui.landing.LandingScreen
import com.alvindizon.lists.ui.room.ListDetailsScreen
import com.alvindizon.lists.ui.room.MyListsScreen
import com.alvindizon.lists.ui.sqldelight.SQLDelightListDetailsScreen
import com.alvindizon.lists.ui.sqldelight.SQLDelightListsScreen


@Composable
fun AppNavGraph(navController: NavHostController = rememberNavController()) {
    NavHost(navController, startDestination = "Landing") {
        composable(route = "Landing") {
            LandingScreen(onRoomClick = {
                navController.navigate(route = "MyLists")
            }, onSqlDelightClick = {
                navController.navigate(route = "SQLDelightMyLists")
            })
        }
        composable(route = "MyLists") {
            MyListsScreen(onListClick = { navController.navigate(route = "ListDetails/${it}") })
        }
        composable(route = "ListDetails/{listId}", arguments = listOf(navArgument("listId") { type = NavType.LongType})) {
            ListDetailsScreen(
                onNavigationIconClick = {
                    navController.popBackStack()
                }
            )
        }
        composable(route = "SQLDelightMyLists") {
            SQLDelightListsScreen(onListClick = { navController.navigate(route = "SQLDelightListDetails/${it}") })
        }
        composable(route = "SQLDelightListDetails/{listId}", arguments = listOf(navArgument("listId") { type = NavType.LongType})) {
            SQLDelightListDetailsScreen(
                onNavigationIconClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}

