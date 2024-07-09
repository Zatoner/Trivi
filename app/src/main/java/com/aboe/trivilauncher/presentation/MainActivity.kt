package com.aboe.trivilauncher.presentation

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.aboe.trivilauncher.ui.theme.TriviLauncherTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TriviLauncherTheme {

                val viewModel: HomeViewModel = hiltViewModel()

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Button(
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxWidth(),
                        onClick = {
                            viewModel.getNotifications()
                        }
                    ) {
                        Text(text = "Get Notifications")
                    }

                    RequestNotificationPermissionScreen(Modifier.padding(innerPadding))

                }
            }
        }
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