package com.aboe.trivilauncher.domain.model

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class WeatherItem(
    val main: String,
    val time: Long,
    val description: String,
    val temp: Double,
    val feelsLike: Double,
    val humidity: Int,
    val windSpeed: Double,
    val clouds: Int,
    val visibility: Int
) {
    override fun toString(): String {
        val timestamp = SimpleDateFormat(
            "yyyy-MM-dd HH:mm",
            Locale.getDefault()
        ).format(Date(time * 1000))

        return """
            Weather at $timestamp:
                main: $main
                description: $description
                temp: $temp °C
                feelsLike: $feelsLike °C
                humidity: $humidity %
                windSpeed: $windSpeed m/s
                clouds: $clouds %
                visibility: $visibility meters
        """.trimIndent()
    }
}