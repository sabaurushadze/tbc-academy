package com.example.academy_tbc.presentation.navigation.auth

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.academy_tbc.presentation.screen.home.HomeScreen
import com.example.academy_tbc.presentation.screen.login.LogInScreen
import com.example.academy_tbc.presentation.screen.onboarding.OnboardingScreen
import com.example.academy_tbc.presentation.screen.register.RegisterScreen
import com.example.academy_tbc.presentation.screen.register_username.RegisterUsernameScreen
import kotlinx.serialization.Serializable

fun NavGraphBuilder.authNavGraph(
    onShowSnackBar: (String) -> Unit,
    navigateToLogin: () -> Unit,
    navigateToRegister: () -> Unit,
    navigateToUserNameCreation: () -> Unit,
    navigateToHome: () -> Unit,
    navigateBack: () -> Unit,
) {

    navigation<AuthNavGraphRoute>(startDestination = OnboardingRoute) {

        composable<OnboardingRoute> {
            OnboardingScreen(
                navigateToLogin = navigateToLogin,
                navigateToRegister = navigateToRegister
            )
        }

        composable<LoginScreenRoute> {
            LogInScreen(
                onShowSnackBar = onShowSnackBar,
                navigateBack = navigateBack,
                navigateToHome = navigateToHome
            )
        }

        composable<RegisterScreenRoute> {
            RegisterScreen(
                onShowSnackBar = onShowSnackBar,
                navigateBack = navigateBack,
                navigateToUserNameCreation = navigateToUserNameCreation
            )
        }

        composable<RegisterUserNameRoute> {
            RegisterUsernameScreen(
                onShowSnackBar = onShowSnackBar,
                navigateBack = navigateBack,
                navigateToHome = navigateToHome
            )
        }

        composable<HomeRoute> {
            HomeScreen(
                onShowSnackBar = onShowSnackBar
            )
        }
    }


}

@Serializable
data object AuthNavGraphRoute

@Serializable
data object OnboardingRoute

@Serializable
data object LoginScreenRoute

@Serializable
data object RegisterScreenRoute

@Serializable
data object RegisterUserNameRoute

@Serializable
data object HomeRoute