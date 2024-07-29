package com.aboe.trivilauncher.data.remote.dto.weather

import com.aboe.trivilauncher.domain.model.WeatherItem
import com.aboe.trivilauncher.domain.model.WeatherWidgetItem


data class WeatherDto(
    val base: String,
    val clouds: Clouds,
    val cod: Int,
    val coord: Coord,
    val dt: Int,
    val id: Int,
    val main: Main,
    val name: String,
    val rain: Rain,
    val sys: Sys,
    val timezone: Int,
    val visibility: Int,
    val weather: List<Weather>,
    val wind: Wind
) {
    fun toWeatherItem() : WeatherItem {
        // maybe add sunset and sunrise for extra context
        return WeatherItem(
            main = weather[0].main,
            time = dt.toLong(),
            description = weather[0].description,
            temp = main.temp,
            feelsLike = main.feelsLike,
            humidity = main.humidity,
            windSpeed = wind.speed,
            clouds = clouds.all,
            visibility = visibility
        )
    }

    fun toWeatherWidgetItem() : WeatherWidgetItem {
        return WeatherWidgetItem(
            temperature = "${main.temp.toInt()}°C",
            iconCode = weather[0].icon
        )
    }
}