package com.aboe.trivilauncher.presentation.nav.components

import android.content.Context
import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
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
    var text by remember { mutableStateOf("") }

    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    fun submitGeminiRequest() {
        onDoneAction(text)
        text = ""
        keyboardController?.hide()
        focusManager.clearFocus()
    }

    Row(
        Modifier
            .fillMaxSize()
            .padding(14.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

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
                            submitGeminiRequest()
                            navController.navigate(targetDestination)
                        }
                    }
                )

                CircleButton(
                    modifier = Modifier
                        .fillMaxHeight(),
                    iconId = if (text.isNotEmpty()) R.drawable.rounded_arrow_forward_48 else R.drawable.rounded_apps_48,
                    onClick = {
                        if (text.isNotEmpty()) {
                            submitGeminiRequest()
                        }
                        navController.navigate(targetDestination)
                    }
                )
            }

            ScreenState.APPS -> {
                // TEMPORARY APPROACH
                val context = LocalContext.current
                val sharedPrefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

                CircleButton(
                    modifier = Modifier
                        .fillMaxHeight(),
                    iconId = R.drawable.rounded_settings_48,
                    onClick = {
                        sharedPrefs.edit().putBoolean("onboarding_completed", false).apply()

                        Toast.makeText(
                            context,
                            "Please restart the app to complete onboarding",
                            Toast.LENGTH_LONG
                        ).show()

                        navController.popBackStack(Path.HomeScreen, false)
                    }
                )

                InputTextField(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    text = "",
                    placeholder = "Search Apps...",
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
                    iconId = R.drawable.rounded_arrow_downward_48,
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
                            submitGeminiRequest()
                        }
                    }
                )

                CircleButton(
                    modifier = Modifier
                        .fillMaxHeight(),
                    iconId = if (text.isNotEmpty()) R.drawable.rounded_arrow_forward_48 else R.drawable.rounded_arrow_downward_48,
                    onClick = {
                        if (text.isNotEmpty()) {
                            submitGeminiRequest()
                        } else {
                            navController.popBackStack(Path.HomeScreen, false)
                        }
                    }
                )
            }
        }
    }
}

