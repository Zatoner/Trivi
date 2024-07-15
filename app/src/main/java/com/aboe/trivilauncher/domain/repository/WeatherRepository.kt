package com.aboe.trivilauncher.domain.repository

import com.aboe.trivilauncher.domain.model.WeatherItem

interface WeatherRepository {

    suspend fun getWeather(lon: Double, lat: Double) : WeatherItem

    suspend fun getForecast(lon: Double, lat: Double) : List<WeatherItem>

//    fun getWidgetWeather(lon: Double, lat: Double) :

}