package com.aboe.trivilauncher.domain.use_case.get_weather_forecast

import android.util.Log
import com.aboe.trivilauncher.domain.repository.WeatherRepository
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
                appendLine("-- Current Weather --")
                appendLine(weather)
                appendLine("-- Weather Forecast --")
                forecast.forEach { weather ->
                    appendLine(weather)
                }
            }

            result
        } catch (e: Exception) {
            Log.e(TAG, "Error fetching weather data ${e.message}")
            "No Weather Info"
        }
    }

}