package com.aboe.trivilauncher.presentation.home.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

@Composable
fun TypingText(
    modifier: Modifier = Modifier,
    text: String,
    animate: Boolean = true,
    animationCallback: () -> Unit = {},
    charactersPerSecond: Int = 50
) {
    var displayedText by remember { mutableStateOf("") }
    val animationProgress = remember { Animatable(0.0f) }

    if (animate) {
        LaunchedEffect(text) {
            animationProgress.animateTo(
                targetValue = 1.0f,
                animationSpec = tween(durationMillis = (text.length * 1000) / charactersPerSecond)
            )
            animationCallback()
        }

        val numCharsToDisplay = (animationProgress.value * text.length).toInt()
        displayedText = text.substring(0, numCharsToDisplay)

        Text(text = displayedText, modifier = modifier, style = MaterialTheme.typography.bodyLarge)
    } else {
        Text(text = text, modifier = modifier, style = MaterialTheme.typography.bodyLarge)
    }
}