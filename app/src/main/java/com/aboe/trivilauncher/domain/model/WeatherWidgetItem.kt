package com.aboe.trivilauncher.domain.model

data class WeatherWidgetItem(
    val temperature: String,
    val iconCode: String,
    val iconResource: Int? = null,
)
