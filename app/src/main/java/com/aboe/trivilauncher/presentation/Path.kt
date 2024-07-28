package com.aboe.trivilauncher.presentation

import kotlinx.serialization.Serializable

sealed class Path {
    @Serializable
    object AppsScreen
    @Serializable
    object GeminiScreen
    @Serializable
    object HomeScreen
}