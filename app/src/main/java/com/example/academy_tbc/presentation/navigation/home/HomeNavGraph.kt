package com.example.academy_tbc.presentation.navigation.home

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.academy_tbc.presentation.screen.home.HomeScreen
import kotlinx.serialization.Serializable

fun NavGraphBuilder.homeNavGraph(
    onShowSnackBar: (String) -> Unit,
) {

    composable<HomeRoute> {
        HomeScreen(
            onShowSnackBar = onShowSnackBar
        )
    }


}

@Serializable
data object HomeRoute