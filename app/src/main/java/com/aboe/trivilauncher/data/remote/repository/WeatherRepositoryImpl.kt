package com.aboe.trivilauncher.data.remote.repository

import com.aboe.trivilauncher.data.remote.OpenWeatherApi
import com.aboe.trivilauncher.domain.model.WeatherItem
import com.aboe.trivilauncher.domain.repository.WeatherRepository
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val weatherApi: OpenWeatherApi
) : WeatherRepository {

    override suspend fun getWeather(lon: Double, lat: Double): WeatherItem {
        return weatherApi.getWeather(lon, lat).toWeatherItem()
    }

    override suspend fun getForecast(lon: Double, lat: Double): List<WeatherItem> {
        return weatherApi.getForecast(lon, lat).toWeatherItems()
    }

}