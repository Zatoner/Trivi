package com.aboe.trivilauncher.data.remote.dto.forecast

import com.aboe.trivilauncher.domain.model.WeatherItem


data class ForecastDto(
    val city: City,
    val cnt: Int,
    val cod: String,
    val list: List<WeatherComp>,
    val message: Int
) {
    fun toWeatherItems() : List<WeatherItem> {
        return list.map { it.toWeatherItem() }
    }
}