package com.aboe.trivilauncher.data.remote.dto.forecast


import com.google.gson.annotations.SerializedName

data class ForecastDto(
    val city: City,
    val cnt: Int,
    val cod: String,
    val list: List<weatherItem>,
    val message: Int
)