package com.example.academy_tbc.presentation.screen.home.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.academy_tbc.presentation.screen.home.HomeScreen
import kotlinx.serialization.Serializable

fun NavGraphBuilder.homeNavGraph() {

    composable<HomeScreenRoute> {
        HomeScreen()
    }


}

@Serializable
data object HomeScreenRoute