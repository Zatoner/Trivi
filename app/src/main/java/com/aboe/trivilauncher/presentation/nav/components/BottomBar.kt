package com.aboe.trivilauncher.presentation.nav.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.aboe.trivilauncher.R
import com.aboe.trivilauncher.presentation.nav.Path
import com.aboe.trivilauncher.presentation.nav.ScreenState

@Composable
fun BottomBar(
    navController: NavHostController,
    screenState: ScreenState,
) {
    Row(
        Modifier
            .fillMaxSize()
            .padding(14.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        when (screenState) {
            ScreenState.HOME -> {

                OutlinedTextField(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    value = "",
                    placeholder = {
                        Text(text = "Ask Gemini...")
                    },
                    shape = RoundedCornerShape(50),
                    singleLine = true,
                    textStyle = TextStyle(textAlign = TextAlign.Start),
                    onValueChange = {

                    })

                Button(
                    modifier = Modifier
                        .aspectRatio(1f)
                        .fillMaxHeight(),
                    shape = CircleShape,
                    colors = ButtonDefaults.buttonColors(),
                    contentPadding = PaddingValues(12.dp),
                    onClick = {
                        navController.navigate(Path.AppsScreen)
                    }) {
                    Icon(
                        modifier = Modifier.fillMaxSize(),
                        imageVector = ImageVector.vectorResource(id = R.drawable.outline_spotlight_48),
                        contentDescription = "Send"
                    )
                }
            }
            ScreenState.APPS -> {

                Button(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    shape = RoundedCornerShape(50),
                    colors = ButtonDefaults.buttonColors(),
                    onClick = {

                }) {
                    Text(text = "Settings")
                }

                Button(
                    modifier = Modifier
                        .aspectRatio(1f)
                        .fillMaxHeight(),
                    shape = CircleShape,
                    colors = ButtonDefaults.buttonColors(),
                    contentPadding = PaddingValues(12.dp),
                    onClick = {
                        navController.popBackStack(Path.HomeScreen, inclusive = false)
                    }) {
                    Icon(
                        modifier = Modifier.fillMaxSize(),
                        imageVector = ImageVector.vectorResource(id = R.drawable.outline_spotlight_48),
                        contentDescription = "Send"
                    )
                }
            }
            ScreenState.GEMINI -> TODO()
        }
    }
}