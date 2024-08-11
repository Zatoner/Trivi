package com.aboe.trivilauncher.presentation.home.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import com.aboe.trivilauncher.R
import com.aboe.trivilauncher.common.Resource
import com.aboe.trivilauncher.presentation.home.HomeUIEvent
import com.aboe.trivilauncher.presentation.home.HomeViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun HomeScreen(
    snackbarHostState : SnackbarHostState,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val dateState by viewModel.dateState
    val weatherState by viewModel.weatherState

    val geminiState by viewModel.geminiState

    LifecycleEventEffect(Lifecycle.Event.ON_RESUME) {
        viewModel.updateContents()
    }

    LaunchedEffect(viewModel) {
        //maybe move this up to navscreen
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
            verticalArrangement = Arrangement.spacedBy(24.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(36.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {

                    // maybe make it into single a composable
                    if (dateState.isNotEmpty()) {
                        Text(text = dateState, style = MaterialTheme.typography.titleLarge)
                    }

                    VerticalDivider(
                        thickness = 1.5.dp,
                        color = Color.Black,
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .height(24.dp)
                    )

                    when (weatherState) {
                        is Resource.Success -> weatherState.data?.let { data ->
                            WeatherWidget(weatherItem = data)
                        }

                        is Resource.Loading -> Text(text = "...", style = MaterialTheme.typography.titleLarge)
                        is Resource.Error -> Text(text = weatherState.message ?: "Error", style = MaterialTheme.typography.titleLarge)
                    }
                }
            }
            item {
                PillLabel(text = "Spotlight", icon = ImageVector.vectorResource(id = R.drawable.outline_spotlight_48))
                Spacer(modifier = Modifier.height(8.dp))

                // make into one composable
                when (geminiState) {
                    is Resource.Success -> geminiState.data?.let { data ->
                        GeminiResponseContent(
                            modifier = Modifier
                                .fillMaxWidth()
                                .animateContentSize(),
                            data = data,
                            textAnimationCallback = {
                                viewModel.completeGeminiAnimationState()
                            },
                            onAppClick = { packageName ->
                                viewModel.launchApp(packageName)
                            }
                        )
                    }

                    is Resource.Loading -> Text(text = "Generating...", style = MaterialTheme.typography.bodyLarge) //make shimmer instead
                    is Resource.Error -> Text(text = geminiState.message ?: "Error", style = MaterialTheme.typography.bodyLarge)
                }
            }
//            item {
//                Spacer(modifier = Modifier.height(16.dp))
//                PillLabel(text = "Debug", icon = ImageVector.vectorResource(id = R.drawable.outline_question_mark_48))
//                RequestNotificationPermissionScreen()
//                RequestUsageStatsPermissionScreen()
//            }
        }
    }
}
