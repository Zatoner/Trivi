package com.aboe.trivilauncher.presentation.home.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
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
    text: String,
    animate: Boolean = true,
    animationCallback: () -> Unit = {},
    charactersPerSecond: Int = 50,
    modifier: Modifier = Modifier
) {
    var displayedText by remember { mutableStateOf("") }
    val animationProgress = remember { Animatable(0.0f) }

    LaunchedEffect(text) {
        if (animate) {
            animationProgress.animateTo(
                targetValue = 1.0f,
                animationSpec = tween(durationMillis = (text.length * 1000) / charactersPerSecond)
            )
            animationCallback()
        } else {
            displayedText = text
        }
    }

    if (animate) {
        val numCharsToDisplay = (animationProgress.value * text.length).toInt()
        displayedText = text.substring(0, numCharsToDisplay)
    }

    Text(text = displayedText, modifier = modifier)
}