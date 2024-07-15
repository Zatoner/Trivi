package com.aboe.trivilauncher.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aboe.trivilauncher.domain.use_case.get_notifications.GetNotificationsUseCase
import com.aboe.trivilauncher.domain.use_case.get_weather_forecast.GetWeatherForecastUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getNotificationsUseCase: GetNotificationsUseCase,
    private val getWeatherForecastUseCase: GetWeatherForecastUseCase
) : ViewModel() {

    init {
        println("HomeViewModel init")
        viewModelScope.launch {
            println(getWeatherForecastUseCase(0.0, 0.0))
        }
    }

    fun getNotifications() {
        println("Getting notifications...")
        viewModelScope.launch {
            val notifications = getNotificationsUseCase()

            for (n in notifications) {
                println("---Notif---")
                println(n)
            }
        }
    }

}