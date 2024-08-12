package com.aboe.trivilauncher.presentation.onboarding.components

import android.Manifest
import android.content.Intent
import android.content.SharedPreferences
import android.provider.Settings
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.aboe.trivilauncher.presentation.onboarding.OnboardingViewModel

@Composable
fun OnboardingScreen(
    sharedPrefs: SharedPreferences,
    onOnboardingCompleted: () -> Unit,
    viewModel: OnboardingViewModel = hiltViewModel()
) {
    var username by remember { mutableStateOf("") }
    var userInfo by remember { mutableStateOf("") }
    var personalitySetting by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.dp, vertical = 48.dp),
    ) {
        Text(text = "Required Permissions", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(16.dp))
        RequestNotificationPermissionScreen()
        RequestUsageStatsPermissionScreen()
        RequestPermission(permission = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ))
        RequestHomeAppPermissionScreen()
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Optional User Info", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = username,
            onValueChange = {
                username = it
            }, label = {
                Text(text = "Username")
            }
        )
        Spacer(modifier = Modifier.height(4.dp))
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = userInfo,
            onValueChange = {
                userInfo = it
            }, label = {
                Text(text = "User Info")
            }
        )
        Spacer(modifier = Modifier.height(4.dp))
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = personalitySetting,
            onValueChange = {
                personalitySetting = it
            }, label = {
                Text(text = "Gemini Personality Setting")
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                val edit = sharedPrefs.edit()
                edit.putString("username", username)
                edit.putString("userInfo", userInfo)
                edit.putString("personalitySetting", personalitySetting)
                edit.apply()

                onOnboardingCompleted()
            }
        ) {
            Text(text = "Finish Onboarding", color = Color.Black)
        }
    }

}

@Composable
fun RequestPermission(
    modifier: Modifier = Modifier,
    permission: Array<String>,
    onPermissionGranted: () -> Unit = {}
) {
    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        permissions.onEach { result ->
            if (!result.value) {
                Toast.makeText(
                    context,
                    "Please grant all permissions to continue",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    Button(
        modifier = modifier,
        onClick = {
            launcher.launch(permission)
            onPermissionGranted()
        }
    ) {
        Text(text = "Request Permissions", color = Color.Black)
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
        Text(text = "Grant Notification Access", color = Color.Black)
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
            Toast.makeText(
                context,
                "Please grant app usage access to the app",
                Toast.LENGTH_LONG
            ).show()
        }) {
        Text(text = "Grant App Usage Access", color = Color.Black)
    }
}

@Composable
fun RequestHomeAppPermissionScreen(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    Button(modifier = modifier,
        onClick = {
            val intent = Intent(Settings.ACTION_HOME_SETTINGS)
            context.startActivity(intent)
            Toast.makeText(
                context,
                "Please set Trivi Launcher as your home app",
                Toast.LENGTH_LONG
            ).show()
        }) {
        Text(text = "Set as Home App", color = Color.Black)
    }
}