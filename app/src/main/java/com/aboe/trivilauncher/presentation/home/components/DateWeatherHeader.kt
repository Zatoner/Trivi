package com.aboe.trivilauncher.presentation.home.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.aboe.trivilauncher.presentation.home.HomeState

@Composable
fun DateWeatherHeader(
    state: HomeState,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.height(28.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        if (state.currentDateDate.isNotEmpty()) {
            Text(
                modifier = Modifier.align(Alignment.CenterVertically),
                text = state.currentDateDate
            )
        }

        VerticalDivider(
            thickness = 1.5.dp,
            color = Color.Black,
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .fillMaxHeight()
        )

        state.weatherItem?.let { item ->
            Icon(
                // Fix this, improve WeatherWidgetItem
                imageVector = ImageVector.vectorResource(id = item.iconResource!!),
                contentDescription = "Weather Icon",
            )
            Spacer(modifier = Modifier.padding(4.dp))
            Text(text = item.temperature)
        }

        if (state.isWeatherLoading) {
            Text(text = "...")
        }
    }
}