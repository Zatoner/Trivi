package com.aboe.trivilauncher.data.remote

import com.aboe.trivilauncher.BuildConfig
import com.aboe.trivilauncher.common.Constants
import com.aboe.trivilauncher.data.remote.dto.forecast.ForecastDto
import com.aboe.trivilauncher.data.remote.dto.weather.WeatherDto
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherApi {

    @GET("weather")
    suspend fun getWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") apiKey: String = BuildConfig.openWeatherKey,
        @Query("units") units: String = "metric",
        @Query("lang") language: String? = null
    ): WeatherDto

    @GET("forecast")
    suspend fun getForecast(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") apiKey: String = BuildConfig.openWeatherKey,
        @Query("units") units: String = "metric",
        @Query("cnt") count: Int = Constants.MAX_WEATHER_FORECAST_ITEMS,
        @Query("lang") language: String? = null
    ): ForecastDto

    companion object {
        const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
    }

}