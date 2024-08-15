package com.aboe.trivilauncher.presentation.nav.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.aboe.trivilauncher.presentation.apps.components.AppsScreen
import com.aboe.trivilauncher.presentation.gemini.components.GeminiScreen
import com.aboe.trivilauncher.presentation.home.components.HomeScreen
import com.aboe.trivilauncher.presentation.nav.NavViewModel
import com.aboe.trivilauncher.presentation.nav.Path
import com.aboe.trivilauncher.presentation.nav.ScreenState

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun NavScreen(
    viewModel: NavViewModel = hiltViewModel()
) {
    val navController = rememberNavController()
    val snackbarHostState = remember { SnackbarHostState() }

    LifecycleEventEffect(Lifecycle.Event.ON_PAUSE) {
        navController.popBackStack(Path.HomeScreen, false)
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        bottomBar = {
            Column {
                BottomAppBar(
                    modifier = Modifier
                        .height(116.dp)
                        .clip(RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp)),
                    containerColor = MaterialTheme.colorScheme.primary
                ) {
                    BottomBar(
                        modifier = Modifier
                            .fillMaxSize(),
                        navController = navController,
                        screenState = viewModel.screenState.value,
                        onDoneAction = { text ->
                            viewModel.setGeminiText(text)
                        }
                    )
                }

                // use a better approach for soft keyboard
                AnimatedVisibility(
                    visible = WindowInsets.isImeVisible,
                    enter = slideInVertically(animationSpec = tween(300)) { height -> height },
                    exit = slideOutVertically(animationSpec = tween(300)) { height -> height }
                ) {
                    Spacer(modifier =
                    Modifier.height(WindowInsets.ime.asPaddingValues().calculateBottomPadding() - 24.dp))
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Path.HomeScreen,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable<Path.AppsScreen>(
                enterTransition = {
                    slideInVertically { height -> height }
                },
                exitTransition = {
                    slideOutVertically { height -> height }
                }
            ) {
                AppsScreen()
                viewModel.setScreenState(ScreenState.APPS)
            }
            composable<Path.GeminiScreen>(
                enterTransition = {
                    slideInVertically { height -> height }
                },
                exitTransition = {
                    slideOutVertically { height -> height }
                }
            ) {
                GeminiScreen(inputText = viewModel.geminiText)
                viewModel.setScreenState(ScreenState.GEMINI)
            }
            composable<Path.HomeScreen> {
                HomeScreen(snackbarHostState = snackbarHostState)
                viewModel.setScreenState(ScreenState.HOME)
            }
        }
    }

}