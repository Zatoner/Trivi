package com.aboe.trivilauncher.domain.use_case.get_weather_forecast

import com.aboe.trivilauncher.domain.repository.WeatherRepository
import javax.inject.Inject

class GetWeatherForecastUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository
) {

    suspend operator fun invoke(lon: Double, lat: Double): String {
        return weatherRepository.getForecast(lon, lat).toString()
    }

}