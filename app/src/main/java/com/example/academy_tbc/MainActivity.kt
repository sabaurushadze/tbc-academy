package com.example.academy_tbc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.academy_tbc.presentation.navigation.AppNavHost
import com.example.academy_tbc.presentation.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val snackbarHostState = remember { SnackbarHostState() }
            val scope = rememberCoroutineScope()

            AppTheme {
                Scaffold(
                    snackbarHost = {
                        SnackbarHost(
                            modifier = Modifier.windowInsetsPadding(WindowInsets.safeDrawing),
                            hostState = snackbarHostState,
                        )
                    },
                ) {
                    AppNavHost(
                        navController = navController,
                        onShowSnackBar = { message ->
                            if (snackbarHostState.currentSnackbarData == null)
                                scope.launch {
                                    snackbarHostState.showSnackbar(
                                        message,
                                        withDismissAction = true
                                    )
                                }
                        }
                    )
                }
            }
        }
    }
}