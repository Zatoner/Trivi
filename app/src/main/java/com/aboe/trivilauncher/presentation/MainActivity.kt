package com.aboe.trivilauncher.presentation

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.aboe.trivilauncher.presentation.nav.components.NavScreen
import com.aboe.trivilauncher.presentation.onboarding.components.OnboardingScreen
import com.aboe.trivilauncher.ui.theme.TriviLauncherTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TriviLauncherTheme {
                var showOnboarding by remember { mutableStateOf(false) }
                // switch to datastore later
                val sharedPrefs = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

                LaunchedEffect(Unit) {
                    showOnboarding = !sharedPrefs.getBoolean("onboarding_completed", false)
                }

                if (showOnboarding) {
                    // temporary crude onboarding screen, basic perms request
                    // needs to be itw own activity in the future
                    OnboardingScreen(
                        sharedPrefs = sharedPrefs,
                        onOnboardingCompleted = {
                            sharedPrefs.edit().putBoolean("onboarding_completed", true).apply()
                            showOnboarding = false
                        }
                    )
                } else {
                    NavScreen()
                }
            }
        }
    }
}