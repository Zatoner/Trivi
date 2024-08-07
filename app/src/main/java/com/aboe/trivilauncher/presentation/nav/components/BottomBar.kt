package com.aboe.trivilauncher.presentation.nav.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.aboe.trivilauncher.R
import com.aboe.trivilauncher.presentation.nav.Path
import com.aboe.trivilauncher.presentation.nav.ScreenState

@Composable
fun BottomBar(
    navController: NavHostController,
    screenState: ScreenState,
    onDoneAction: (String) -> Unit
) {
    Row(
        Modifier
            .fillMaxSize()
            .padding(14.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        var text by remember { mutableStateOf("") }

        // use AnimatedVisibility
        when (screenState) {
            ScreenState.HOME -> {
                val targetDestination =
                    if (text.isNotEmpty()) Path.GeminiScreen else Path.AppsScreen

                InputTextField(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    text = text,
                    placeholder = "Ask Gemini...",
                    onValueChange = { input ->
                        text = input
                    },
                    onDoneAction = {
                        if (text.isNotEmpty()) {
                            onDoneAction(text)
                            text = ""
                            navController.navigate(targetDestination)
                        }
                    }
                )

                CircleButton(
                    modifier = Modifier
                        .fillMaxHeight(),
                    iconId = if (text.isNotEmpty()) R.drawable.outline_question_mark_48 else R.drawable.outline_spotlight_48,
                    onClick = {
                        if (text.isNotEmpty()) {
                            onDoneAction(text)
                            text = ""
                        }
                        navController.navigate(targetDestination)
                    }
                )
            }

            ScreenState.APPS -> {

                CircleButton(
                    modifier = Modifier
                        .fillMaxHeight(),
                    iconId = R.drawable.outline_question_mark_48,
                    onClick = {
                        //settings
                    }
                )

                InputTextField(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    text = "",
                    placeholder = "Search Apps..",
                    onValueChange = {
                        //search
                    },
                    onDoneAction = {
                        //search
                    }
                )

                CircleButton(
                    modifier = Modifier
                        .fillMaxHeight(),
                    iconId = R.drawable.outline_question_mark_48,
                    onClick = {
                        navController.popBackStack(Path.HomeScreen, false)
                    }
                )
            }

            ScreenState.GEMINI -> {

                InputTextField(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    text = text,
                    placeholder = "Ask Gemini...",
                    onValueChange = { input ->
                        text = input
                    },
                    onDoneAction = {
                        if (text.isNotEmpty()) {
                            onDoneAction(text)
                            text = ""
                        }
                    }
                )

                CircleButton(
                    modifier = Modifier
                        .fillMaxHeight(),
                    iconId = if (text.isNotEmpty()) R.drawable.outline_question_mark_48 else R.drawable.outline_spotlight_48,
                    onClick = {
                        if (text.isNotEmpty()) {
                            onDoneAction(text)
                            text = ""
                        } else {
                            navController.popBackStack(Path.HomeScreen, false)
                        }
                    }
                )
            }
        }
    }
}

