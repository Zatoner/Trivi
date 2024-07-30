package com.aboe.trivilauncher.presentation.home

import com.aboe.trivilauncher.domain.model.GeminiItem
import com.aboe.trivilauncher.domain.model.WeatherWidgetItem

data class HomeState(
    val currentDateDate: String = "",

    // make into data class
    val weatherItem: WeatherWidgetItem? = null,
    val isWeatherLoading: Boolean = false,

    val geminiItem: GeminiItem? = null,
    val isGeminiLoading: Boolean = false
)
