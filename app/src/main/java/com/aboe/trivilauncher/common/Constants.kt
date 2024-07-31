package com.aboe.trivilauncher.common

import com.google.ai.client.generativeai.type.BlockThreshold
import com.google.ai.client.generativeai.type.HarmCategory
import com.google.ai.client.generativeai.type.SafetySetting
import com.google.ai.client.generativeai.type.generationConfig

object Constants {

    const val SYSTEM_PROMPT = """
    You are a friend, human-like, and minimalistic personal assistant integrated into a launcher 
    designed to remove friction when using their phone. Understand the context of their conversations and provide detailed
    information when necessary. Use plain text without formatting, no newlines. Prioritize the most important 
    information first. Address the user directly, like you're having a conversation. Like google pixel at a glance but better.
"""

    const val DEFAULT_PROMPT = """
    Provide a concise update using the given context, aiming for 4-5 sentences. 
    This update will be displayed on the home screen to help reduce screen time, so prioritize the 
    most important and recent information. Summarize key conversations and updates, avoiding mundane 
    details like device status. Consider the time of day: if it’s the end of the day, provide a 
    summary and suggest activities for tomorrow; if it’s the start of the day, suggest relevant 
    actions. If there’s little to report, share interesting facts or other relevant information. 
    Avoid mentioning the time, date, and current weather (weather forecast is fine), as the user is already aware.
"""
    const val MAX_NOTIFICATION_AGE_HOURS = 12

    const val MAX_WEATHER_FORECAST_ITEMS = 8

    const val MAX_APP_USAGE_AGE_DAYS = 7

    const val MIN_APP_USAGE_TIME_MINUTES = 1

    const val MODEL_NAME = "gemini-1.5-pro"

    val GEMINI_CONFIG = generationConfig {
        temperature = 0.6f
        topK = 10
        topP = 0.9f
        maxOutputTokens = 128
    }

    private val HARASSMENT_PARAM = SafetySetting(HarmCategory.HARASSMENT, BlockThreshold.NONE)
    private val HATE_SPEECH_PARAM = SafetySetting(HarmCategory.HATE_SPEECH, BlockThreshold.NONE)
    private val DANGEROUS_CONTENT_PARAM = SafetySetting(HarmCategory.DANGEROUS_CONTENT, BlockThreshold.NONE)
    private val SEXUALLY_EXPLICIT_PARAM = SafetySetting(HarmCategory.SEXUALLY_EXPLICIT, BlockThreshold.NONE)
    val SAFETY_SETTINGS = listOf(HARASSMENT_PARAM, HATE_SPEECH_PARAM, DANGEROUS_CONTENT_PARAM, SEXUALLY_EXPLICIT_PARAM)

}