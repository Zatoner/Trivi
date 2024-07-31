package com.aboe.trivilauncher.domain.model

data class GeminiItem(
    val response: String,
    val hasAnimated: Boolean = false,
    val apps: List<String> = emptyList(),
)
