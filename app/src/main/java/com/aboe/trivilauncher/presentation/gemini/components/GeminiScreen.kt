package com.aboe.trivilauncher.presentation.gemini.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.aboe.trivilauncher.presentation.gemini.GeminiViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collectLatest

@Composable
fun GeminiScreen(
    inputText: MutableSharedFlow<String>,
    viewModel: GeminiViewModel = hiltViewModel()
) {
    LaunchedEffect(inputText) {
        inputText.collectLatest {
            println(it)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Gemini Screen",
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}