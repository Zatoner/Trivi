package com.aboe.trivilauncher.presentation.home.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import com.aboe.trivilauncher.R
import com.aboe.trivilauncher.presentation.home.HomeUIEvent
import com.aboe.trivilauncher.presentation.home.HomeViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun HomeScreen(
    snackbarHostState : SnackbarHostState,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val state = viewModel.state.value

    LifecycleEventEffect(Lifecycle.Event.ON_RESUME) {
        viewModel.updateContents()
    }

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {

                is HomeUIEvent.ShowSnackbar -> {
                    val snackbarResult = snackbarHostState.showSnackbar(
                        message = event.message,
                        duration = SnackbarDuration.Short,
                        actionLabel = "Retry"
                    )

                    when (snackbarResult) {
                        SnackbarResult.Dismissed -> Unit
                        SnackbarResult.ActionPerformed -> {
                            viewModel.resetLastUpdate()
                            viewModel.updateContents()
                        }
                    }
                }

            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 28.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(24.dp))
                DateWeatherHeader(state = state, modifier = Modifier.fillMaxWidth())
            }
            item {
                Spacer(modifier = Modifier.height(24.dp))
                PillLabel(text = "Spotlight", icon = ImageVector.vectorResource(id = R.drawable.outline_spotlight_48))
                Spacer(modifier = Modifier.height(8.dp))

                state.geminiItem?.let { geminiItem ->
                    if (!state.isGeminiLoading) {
                        TypingText(text = geminiItem.response)
                    }
                }

                //handle loading state
            }
            item {
                Spacer(modifier = Modifier.height(128.dp))
                PillLabel(text = "Debug", icon = ImageVector.vectorResource(id = R.drawable.outline_question_mark_48))
                RequestNotificationPermissionScreen()
                RequestUsageStatsPermissionScreen()
            }
        }
    }
}

@Composable
fun TypingText(text: String) {
    var displayedText by remember { mutableStateOf("") }
    val animationProgress = remember { Animatable(0.0f) }

    LaunchedEffect(text) {
        animationProgress.animateTo(
            targetValue = 1.0f,
            animationSpec = tween(durationMillis = text.length * 15) // Adjust timing
        )
    }

    val numCharsToDisplay = (animationProgress.value * text.length).toInt()
    displayedText = text.substring(0, numCharsToDisplay)

    Text(text = displayedText)
}
