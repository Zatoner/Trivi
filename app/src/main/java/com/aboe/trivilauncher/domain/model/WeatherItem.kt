package com.aboe.trivilauncher.domain.model

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
)