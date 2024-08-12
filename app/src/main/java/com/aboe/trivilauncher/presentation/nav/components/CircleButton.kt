package com.aboe.trivilauncher.presentation.nav.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp

@Composable
fun CircleButton(
    modifier: Modifier = Modifier,
    iconId: Int,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier
            .aspectRatio(1f),
        shape = CircleShape,
        colors = ButtonColors(
            containerColor = MaterialTheme.colorScheme.secondary,
            contentColor = Color.Black,
            disabledContainerColor = Color.Gray,
            disabledContentColor = Color.Black
        ),
        contentPadding = PaddingValues(12.dp),
        onClick = {
            onClick()
        }
    ) {
        Icon(
            modifier = Modifier.fillMaxSize(),
            imageVector = ImageVector.vectorResource(id = iconId),
            contentDescription = "Send"
        )
    }
}