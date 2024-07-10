package com.aboe.trivilauncher.data.remote.dto.forecast


import com.google.gson.annotations.SerializedName

data class Wind(
    val deg: Int,
    val gust: Double,
    val speed: Double
)