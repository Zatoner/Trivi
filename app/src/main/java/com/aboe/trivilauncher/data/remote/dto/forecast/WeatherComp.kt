package com.aboe.trivilauncher.data.remote.dto.forecast


import com.aboe.trivilauncher.domain.model.WeatherItem
import com.google.gson.annotations.SerializedName

data class WeatherComp (
    val clouds: Clouds,
    val dt: Int,
    @SerializedName("dt_txt")
    val dtTxt: String,
    val main: Main,
    val pop: Double,
    val rain: Rain?,
    val sys: Sys,
    val visibility: Int,
    val weather: List<Weather>,
    val wind: Wind
) {
    fun toWeatherItem() : WeatherItem {
        return WeatherItem(
            main = weather[0].main,
            time = dt.toLong(),
            description = weather[0].description,
            temp = main.temp,
            feelsLike = main.feelsLike,
            humidity = main.humidity,
            windSpeed = (wind.speed * 3.6).toInt(),
            clouds = clouds.all,
            visibility = visibility
        )
    }
}