package com.aboe.trivilauncher.presentation.apps.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.aboe.trivilauncher.presentation.apps.AppsViewModel

@Composable
fun AppsScreen(
    viewModel: AppsViewModel = hiltViewModel()
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Apps Section",
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}