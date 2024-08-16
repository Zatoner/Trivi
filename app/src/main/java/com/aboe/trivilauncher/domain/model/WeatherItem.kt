package com.aboe.trivilauncher.domain.model

data class WeatherItem(
    val main: String,
    val time: Long,
    val description: String,
    val temp: Int,
    val feelsLike: Int,
    val humidity: Int,
    val windSpeed: Int,
    val clouds: Int,
    val visibility: Int
)