package com.aboe.trivilauncher.data.remote.dto.forecast


import com.google.gson.annotations.SerializedName

data class weatherItem (
    val clouds: Clouds,
    val dt: Int,
    @SerializedName("dt_txt")
    val dtTxt: String,
    val main: Main,
    val pop: Double,
    val rain: Rain?,
    val sys: Sys,
    val visibility: Int,
    val weather: List<Weather>,
    val wind: Wind
)