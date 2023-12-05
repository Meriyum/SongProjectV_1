

package com.example.songprojectv_1.ui.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

import com.example.songprojectv_1.ui.song.ItemEntryScreen
import com.example.songprojectv_1.ui.song.SongEntryDestination

import com.example.songprojectv_1.ui.song.SongDetailsDestination
import com.example.songprojectv_1.ui.home.HomeDestination
import com.example.songprojectv_1.ui.home.HomeScreen
import com.example.songprojectv_1.ui.song.SongDetailsScreen
import com.example.songprojectv_1.ui.song.SongEditDestination
import com.example.songprojectv_1.ui.song.SongEditScreen


@Composable
fun SongsNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController, startDestination = HomeDestination.route, modifier = modifier
    ) {
        composable(route = HomeDestination.route) {
            HomeScreen(navigateToSongEntry = { navController.navigate(SongEntryDestination.route) },
                navigateToSongUpdate = {
                    Log.d("Arga", "abc${it}")
                    navController.navigate("${SongDetailsDestination.route}/${it}")
                })
        }
        composable(route = SongEntryDestination.route) {
            ItemEntryScreen(navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() })
        }
        composable(
            route = SongDetailsDestination.routeWithArgs,
            arguments = listOf(navArgument(SongDetailsDestination.songIdArg) {
                type = NavType.IntType
            })
       )
            {
                SongDetailsScreen(
                    navigateToEditItem =
                    {
                        Log.d("Arg", "abc${it}")
                    navController.navigate("${SongEditDestination.route}/$it")
                    },
                    navigateBack = { navController.navigateUp() })
            }
        composable(
            route = SongEditDestination.routeWithArgs,
            arguments = listOf(navArgument(SongEditDestination.songIdArg) {
                type = NavType.IntType
            })
        ) {
            SongEditScreen(navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() })
        }
    }
}