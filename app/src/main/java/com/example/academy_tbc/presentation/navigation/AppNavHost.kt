package com.example.academy_tbc.presentation.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.academy_tbc.presentation.navigation.auth.AuthNavGraphRoute
import com.example.academy_tbc.presentation.navigation.auth.HomeRoute
import com.example.academy_tbc.presentation.navigation.auth.LoginScreenRoute
import com.example.academy_tbc.presentation.navigation.auth.RegisterScreenRoute
import com.example.academy_tbc.presentation.navigation.auth.RegisterUserNameRoute
import com.example.academy_tbc.presentation.navigation.auth.authNavGraph

@Composable
fun AppNavHost(
    navController: NavHostController,
    onShowSnackBar: (String) -> Unit,
) {
    NavHost(
        navController = navController,
        startDestination = AuthNavGraphRoute,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = { ExitTransition.None }
    ) {
        authNavGraph(
            onShowSnackBar = onShowSnackBar,
            navigateToLogin = {
                navController.navigate(LoginScreenRoute)
            },
            navigateToRegister = {
                navController.navigate(RegisterScreenRoute)
            },
            navigateBack = {
                navController.navigateUp()
            },
            navigateToUserNameCreation = {
                navController.navigate(RegisterUserNameRoute)
            },
            navigateToHome = {
                navController.navigate(HomeRoute) {
                    popUpTo(AuthNavGraphRoute) { inclusive = true }
                    launchSingleTop = true
                }
            },
        )
    }
}