package com.aboe.trivilauncher.domain.repository

import com.aboe.trivilauncher.domain.model.WeatherItem
import com.aboe.trivilauncher.domain.model.WeatherWidgetItem

interface WeatherRepository {

    suspend fun getWeather(lon: Double, lat: Double) : WeatherItem

    suspend fun getWeatherWidget(lon: Double, lat: Double) : WeatherWidgetItem

    suspend fun getForecast(lon: Double, lat: Double) : List<WeatherItem>

}