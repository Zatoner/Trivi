package com.aboe.trivilauncher.data.remote.dto.forecast


data class Weather(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
)