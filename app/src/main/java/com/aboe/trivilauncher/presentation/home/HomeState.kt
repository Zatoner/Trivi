package com.aboe.trivilauncher.presentation.home

import com.aboe.trivilauncher.domain.model.WeatherWidgetItem

data class HomeState(
    val weatherItem: WeatherWidgetItem? = null,
    val isWeatherLoading: Boolean = false,
    val currentDateDate: String = ""
)
