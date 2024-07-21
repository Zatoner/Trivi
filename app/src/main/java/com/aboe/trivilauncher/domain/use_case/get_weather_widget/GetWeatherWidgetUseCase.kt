package com.aboe.trivilauncher.domain.use_case.get_weather_widget

import android.util.Log
import com.aboe.trivilauncher.R
import com.aboe.trivilauncher.domain.model.WeatherWidgetItem
import com.aboe.trivilauncher.domain.repository.WeatherRepository
import com.aboe.trivilauncher.domain.use_case.get_user_location.GetUserLocationUseCase
import javax.inject.Inject

class GetWeatherWidgetUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val getUserLocationUseCase: GetUserLocationUseCase,
) {
    val TAG = "GetWeatherWidgetUseCase"

    // convert to flow of result
    suspend operator fun invoke() : WeatherWidgetItem {
        val location = getUserLocationUseCase() ?: return WeatherWidgetItem(
            temperature = "NA°C",
            icon = "0",
            iconResource = "-1".toIconResource()
        )

        return try {
            val weatherWidget = weatherRepository.getWeatherWidget(
                lon = location.longitude,
                lat = location.latitude
            )

            weatherWidget.copy(
                iconResource = weatherWidget.icon.toIconResource()
            )
        } catch (e: Exception) {
            Log.e(TAG, "Error fetching weather data ${e.message}")
            WeatherWidgetItem(
                temperature = "NA°C",
                icon = "0",
                iconResource = "-1".toIconResource()
            )
        }
    }

    private fun String.toIconResource(): Int {
        return when (this) {
            "01d" -> R.drawable.outline_clear_day_48
            "01n" -> R.drawable.outline_nightlight_48
            "02d" -> R.drawable.outline_partly_cloudy_day_48
            "02n" -> R.drawable.outline_partly_cloudy_night_48
            "03d" -> R.drawable.outline_cloud_48
            "03n" -> R.drawable.outline_cloud_48
            "04d" -> R.drawable.outline_cloud_48
            "04n" -> R.drawable.outline_cloud_48
            "09d" -> R.drawable.outline_rainy_48
            "09n" -> R.drawable.outline_rainy_48
            "10d" -> R.drawable.outline_rainy_48
            "10n" -> R.drawable.outline_rainy_48
            "11d" -> R.drawable.outline_thunderstorm_48
            "11n" -> R.drawable.outline_thunderstorm_48
            "13d" -> R.drawable.outline_weather_snowy_48
            "13n" -> R.drawable.outline_weather_snowy_48
            "50d" -> R.drawable.outline_foggy_48
            "50n" -> R.drawable.outline_foggy_48
            "-1" -> R.drawable.outline_question_mark_48
            else -> R.drawable.outline_question_mark_48
        }
    }
}