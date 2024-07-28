package com.aboe.trivilauncher.presentation.home.components

import android.content.Intent
import android.provider.Settings
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.aboe.trivilauncher.presentation.home.HomeViewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Home Section",
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@Composable
fun RequestNotificationPermissionScreen(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    Button(modifier = modifier,
        onClick = {
            val intent = Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS)
            context.startActivity(intent)
            Toast.makeText(
                context,
                "Please grant notification access to the app",
                Toast.LENGTH_LONG
            ).show()
        }) {
        Text(text = "Grant Notification Access")
    }
}

@Composable
fun RequestUsageStatsPermissionScreen(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    Button(modifier = modifier,
        onClick = {
            val intent = Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS)
            context.startActivity(intent)
        }) {
        Text(text = "Grant App Usage Access")
    }
}