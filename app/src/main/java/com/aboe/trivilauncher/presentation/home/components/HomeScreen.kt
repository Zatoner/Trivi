package com.aboe.trivilauncher.presentation.home.components

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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
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
                Spacer(modifier = Modifier.height(64.dp))
                RequestNotificationPermissionScreen()
                RequestUsageStatsPermissionScreen()
            }
        }
    }
}
