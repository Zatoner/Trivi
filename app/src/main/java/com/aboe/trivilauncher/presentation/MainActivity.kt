package com.aboe.trivilauncher.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.aboe.trivilauncher.presentation.apps.components.AppsScreen
import com.aboe.trivilauncher.presentation.gemini.components.GeminiScreen
import com.aboe.trivilauncher.presentation.home.components.HomeScreen
import com.aboe.trivilauncher.presentation.main.components.MainScreen
import com.aboe.trivilauncher.ui.theme.TriviLauncherTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onResume() {
        super.onResume()
        println("MainActivity onResume")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            TriviLauncherTheme {
                val navController = rememberNavController()
                val snackbarHostState = remember { SnackbarHostState() }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    snackbarHost = { snackbarHostState },
                    bottomBar = {
                        BottomAppBar(
                            containerColor = MaterialTheme.colorScheme.secondary
                        ) {
                            MainScreen(navController = navController)
                        }
                    }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = Path.HomeScreen,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable<Path.AppsScreen> {
                            AppsScreen()
                        }
                        composable<Path.GeminiScreen> {
                            GeminiScreen()
                        }
                        composable<Path.HomeScreen> {
                            HomeScreen()
                        }
                    }
                }
            }
        }
    }
}