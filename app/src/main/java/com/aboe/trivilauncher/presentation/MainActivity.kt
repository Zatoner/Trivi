package com.aboe.trivilauncher.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.aboe.trivilauncher.presentation.nav.components.NavScreen
import com.aboe.trivilauncher.ui.theme.TriviLauncherTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TriviLauncherTheme {
                NavScreen()
            }
        }
    }
}