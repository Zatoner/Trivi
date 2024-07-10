package com.aboe.trivilauncher.data.remote

import com.aboe.trivilauncher.BuildConfig
import com.aboe.trivilauncher.data.remote.dto.forecast.ForecastDto
import com.aboe.trivilauncher.data.remote.dto.weather.WeatherDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface OpenWeatherApi {

    @GET("/weather")
    suspend fun getWeather(
        @Path("lat") lat: Double,
        @Path("lon") lon: Double,
        @Query("appid") apiKey: String = BuildConfig.openWeatherKey,
        @Query("units") units: String = "metric",
        @Query("lang") language: String? = null
    ): WeatherDto

    @GET("/forecast")
    suspend fun getForecast(
        @Path("lat") lat: Double,
        @Path("lon") lon: Double,
        @Query("appid") apiKey: String = BuildConfig.openWeatherKey,
        @Query("units") units: String = "metric",
        @Query("cnt") count: Int = 8,
        @Query("lang") language: String? = null
    ): ForecastDto

    companion object {
        const val BASE_URL = "https://api.openweathermap.org/data/2.5"
    }

}