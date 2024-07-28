package com.aboe.trivilauncher.presentation.gemini.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.aboe.trivilauncher.presentation.gemini.GeminiViewModel

@Composable
fun GeminiScreen(
    viewModel: GeminiViewModel = hiltViewModel()
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Gemini Section",
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}