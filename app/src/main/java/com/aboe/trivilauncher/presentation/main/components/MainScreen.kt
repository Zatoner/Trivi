package com.aboe.trivilauncher.presentation.main.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.aboe.trivilauncher.presentation.Path
import com.aboe.trivilauncher.presentation.main.MainViewModel

@Composable
fun MainScreen(
    navController: NavHostController,
    viewModel: MainViewModel = hiltViewModel()
) {
    Row(
        Modifier.fillMaxSize()
    ) {
        Button(
            onClick = {
                navController.navigate(Path.GeminiScreen)
            }
        ) {
            Text(text = "Gemini")
        }
        Button(
            onClick = {
                navController.navigate(Path.AppsScreen)
            }
        ) {
            Text(text = "Apps")
        }
        Button(
            onClick = {
                navController.popBackStack(route = Path.HomeScreen, inclusive = false)
            }
        ) {
            Text(text = "Back")
        }
    }
}