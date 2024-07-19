package com.aboe.trivilauncher.common

import com.google.ai.client.generativeai.type.BlockThreshold
import com.google.ai.client.generativeai.type.HarmCategory
import com.google.ai.client.generativeai.type.SafetySetting
import com.google.ai.client.generativeai.type.generationConfig

object Constants {

    const val SYSTEM_PROMPT = "you're my personal assistant super useful to the point, no need to tell info that the user already knows."

    const val DEFAULT_PROMPT = "Status update, list anything you find important from the context that is provided."

    const val MAX_NOTIFICATION_AGE_HOURS = 12

    const val MAX_WEATHER_FORECAST_ITEMS = 8

    const val MAX_APP_USAGE_AGE_DAYS = 7

    const val MIN_APP_USAGE_TIME_MINUTES = 1

    val GEMINI_CONFIG = generationConfig {
        temperature = 0.4f
        maxOutputTokens = 128
    }

    private val HARASSMENT_PARAM = SafetySetting(HarmCategory.HARASSMENT, BlockThreshold.MEDIUM_AND_ABOVE)
    private val HATE_SPEECH_PARAM = SafetySetting(HarmCategory.HATE_SPEECH, BlockThreshold.ONLY_HIGH)
    private val DANGEROUS_CONTENT_PARAM = SafetySetting(HarmCategory.DANGEROUS_CONTENT, BlockThreshold.MEDIUM_AND_ABOVE)
    private val SEXUALLY_EXPLICIT_PARAM = SafetySetting(HarmCategory.SEXUALLY_EXPLICIT, BlockThreshold.LOW_AND_ABOVE)
    val SAFETY_SETTINGS = listOf(HARASSMENT_PARAM, HATE_SPEECH_PARAM, DANGEROUS_CONTENT_PARAM, SEXUALLY_EXPLICIT_PARAM)

}