package com.aboe.trivilauncher.presentation.home.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aboe.trivilauncher.R
import com.aboe.trivilauncher.domain.model.WeatherWidgetItem
import com.aboe.trivilauncher.presentation.home.HomeState
import com.aboe.trivilauncher.ui.theme.TriviLauncherTheme

@Composable
fun DateWeatherHeader(
    state: HomeState,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
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
                .height(24.dp)
        )

        state.weatherItem?.let { item ->
            Icon(
                // Fix this, improve WeatherWidgetItem
                imageVector = ImageVector.vectorResource(id = item.iconResource!!),
                contentDescription = "Weather Icon",
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.padding(4.dp))
            Text(text = item.temperature)
        }

        if (state.isWeatherLoading) {
            Text(text = "...")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DateWeatherHeaderPreview() {
    TriviLauncherTheme {
        DateWeatherHeader(
            state = HomeState(
                currentDateDate = "Tuesday, 21 November",
                weatherItem = WeatherWidgetItem(
                    temperature = "15°C",
                    iconCode = "01d", // Example icon code
                    iconResource = R.drawable.outline_partly_cloudy_day_48// Replace withyour actual icon resource
                ),
                isWeatherLoading = false
            )
        )
    }
}