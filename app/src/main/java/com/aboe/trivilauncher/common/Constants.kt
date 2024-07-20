package com.aboe.trivilauncher.common

import com.google.ai.client.generativeai.type.BlockThreshold
import com.google.ai.client.generativeai.type.HarmCategory
import com.google.ai.client.generativeai.type.SafetySetting
import com.google.ai.client.generativeai.type.generationConfig

object Constants {

    const val SYSTEM_PROMPT = "you're my personal assistant"

    const val DEFAULT_PROMPT = "Status update, list anything you find important from the context that is provided. you have 128 tokens."

    const val MAX_NOTIFICATION_AGE_HOURS = 12

    const val MAX_WEATHER_FORECAST_ITEMS = 8

    const val MAX_APP_USAGE_AGE_DAYS = 7

    const val MIN_APP_USAGE_TIME_MINUTES = 1

    val GEMINI_CONFIG = generationConfig {
        temperature = 0.8f
        maxOutputTokens = 128
    }

    private val HARASSMENT_PARAM = SafetySetting(HarmCategory.HARASSMENT, BlockThreshold.NONE)
    private val HATE_SPEECH_PARAM = SafetySetting(HarmCategory.HATE_SPEECH, BlockThreshold.NONE)
    private val DANGEROUS_CONTENT_PARAM = SafetySetting(HarmCategory.DANGEROUS_CONTENT, BlockThreshold.NONE)
    private val SEXUALLY_EXPLICIT_PARAM = SafetySetting(HarmCategory.SEXUALLY_EXPLICIT, BlockThreshold.NONE)
    val SAFETY_SETTINGS = listOf(HARASSMENT_PARAM, HATE_SPEECH_PARAM, DANGEROUS_CONTENT_PARAM, SEXUALLY_EXPLICIT_PARAM)

}