package com.aboe.trivilauncher.presentation.home.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.aboe.trivilauncher.domain.model.WeatherWidgetItem

@Composable
fun WeatherWidget(weatherItem: WeatherWidgetItem) {
    Icon(
        // Fix this, improve WeatherWidgetItem
        imageVector = ImageVector.vectorResource(id = weatherItem.iconResource!!),
        contentDescription = "Weather Icon",
        modifier = Modifier.size(24.dp)
    )
    Spacer(modifier = Modifier.padding(4.dp))
    Text(text = weatherItem.temperature)
}