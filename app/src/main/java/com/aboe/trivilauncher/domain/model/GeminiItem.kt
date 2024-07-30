package com.aboe.trivilauncher.domain.model

data class GeminiItem(
    val response: String,
    val apps: List<String> = emptyList(),
)
