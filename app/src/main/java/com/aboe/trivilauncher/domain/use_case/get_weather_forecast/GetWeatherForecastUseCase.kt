package com.aboe.trivilauncher.domain.use_case.get_weather_forecast

import android.util.Log
import com.aboe.trivilauncher.domain.repository.WeatherRepository
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

class GetWeatherForecastUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository
) {
    val TAG = "GetWeatherForecastUseCase"

    suspend operator fun invoke(lon: Double, lat: Double): String {
        return try {
            val weather = weatherRepository.getWeather(lon, lat)
            val forecast = weatherRepository.getForecast(lon, lat)

            val result = buildString {
                // move to data class
                appendLine("- Now: ${weather.main}, Description: ${weather.description}, " +
                        "Temp: ${weather.temp} °C, Feels Like: ${weather.feelsLike} °C, " +
                        "Humidity: ${weather.humidity}%, Wind Speed: ${weather.windSpeed} m/s, " +
                        "Clouds: ${weather.clouds}%, Visibility: ${weather.visibility} meters")

                forecast.forEach { weather ->

                    val time = SimpleDateFormat(
                        "HH:mm",
                        Locale.getDefault()
                    ).format(Date(weather.time * 1000))

                    // move to data class
                    appendLine("- $time, ${weather.description}, " +
                            "${weather.temp} °C, Humidity: ${weather.humidity}%, " +
                            "Wind Speed: ${weather.windSpeed} m/s")
                }
            }

            result
        } catch (e: Exception) {
            Log.e(TAG, "Error fetching weather data ${e.message}")
            "No Weather Info"
        }
    }
}