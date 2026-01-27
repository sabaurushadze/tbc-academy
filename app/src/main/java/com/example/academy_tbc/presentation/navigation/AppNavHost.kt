package com.example.academy_tbc.presentation.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.academy_tbc.presentation.navigation.auth.HomeScreenRoute
import com.example.academy_tbc.presentation.navigation.auth.homeNavGraph

@Composable
fun AppNavHost(
    navController: NavHostController,
    onShowSnackBar: (String) -> Unit,
) {
    NavHost(
        navController = navController,
        startDestination = HomeScreenRoute,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = { ExitTransition.None }
    ) {
        homeNavGraph(
            onShowSnackBar = onShowSnackBar,
        )
    }
}