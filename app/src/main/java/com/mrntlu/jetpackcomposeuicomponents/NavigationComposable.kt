package com.mrntlu.jetpackcomposeuicomponents

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.mrntlu.jetpackcomposeuicomponents.ui.HomeScreen
import com.mrntlu.jetpackcomposeuicomponents.ui.AddScreen
import com.mrntlu.jetpackcomposeuicomponents.ui.SettingsScreen
import com.mrntlu.jetpackcomposeuicomponents.viewmodels.SharedViewModel

@Composable
fun NavigationComposable(
    navController: NavHostController,
    sharedViewModel: SharedViewModel,
) {
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(
                navController = navController,
                sharedViewModel = sharedViewModel,
            )
        }

        composable("add") {
            AddScreen()
        }

        composable("settings") {
            SettingsScreen(
                sharedViewModel = sharedViewModel,
            )
        }
    }
}