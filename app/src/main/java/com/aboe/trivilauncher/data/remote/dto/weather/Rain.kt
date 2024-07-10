package com.aboe.trivilauncher.data.remote.dto.weather


import com.google.gson.annotations.SerializedName

data class Rain(
    @SerializedName("1h")
    val h: Double
)