package com.aboe.trivilauncher.common

import com.google.ai.client.generativeai.type.BlockThreshold
import com.google.ai.client.generativeai.type.HarmCategory
import com.google.ai.client.generativeai.type.SafetySetting
import com.google.ai.client.generativeai.type.generationConfig

object Constants {

    const val SYSTEM_PROMPT = """
    You are my conversational and talkative like a human, minimalistic personal assistant integrated into a 
    launcher designed to reduce screen time. Provide detailed information and understand the context of my conversations. Use plain text without formatting. 
    Prioritize the most important information first. aim for 4 - 5 sentences. Address the user in first person, like you're talking to them.
"""

    const val DEFAULT_PROMPT = """
    Provide a update using the context. This update will be displayed on the 
    home screen to help reduce screen time, so prioritize the most important and recent information. 
    Focus on summarizing key conversations and updates, avoiding mundane details like device status. 
    Start with notifications, then include other updates. If there's little to say, include 
    interesting facts about the user's location or app usage. Avoid mentioning the time and date and current
    weather (weather forecast is fine) as the users is already aware. Suggest 2-3 apps the user should open right 
    now based on your response, listing only the app names.
"""

    const val MAX_NOTIFICATION_AGE_HOURS = 12

    const val MAX_WEATHER_FORECAST_ITEMS = 8

    const val MAX_APP_USAGE_AGE_DAYS = 7

    const val MIN_APP_USAGE_TIME_MINUTES = 1

    val GEMINI_CONFIG = generationConfig {
        temperature = 0.8f
        maxOutputTokens = 256
    }

    private val HARASSMENT_PARAM = SafetySetting(HarmCategory.HARASSMENT, BlockThreshold.NONE)
    private val HATE_SPEECH_PARAM = SafetySetting(HarmCategory.HATE_SPEECH, BlockThreshold.NONE)
    private val DANGEROUS_CONTENT_PARAM = SafetySetting(HarmCategory.DANGEROUS_CONTENT, BlockThreshold.NONE)
    private val SEXUALLY_EXPLICIT_PARAM = SafetySetting(HarmCategory.SEXUALLY_EXPLICIT, BlockThreshold.NONE)
    val SAFETY_SETTINGS = listOf(HARASSMENT_PARAM, HATE_SPEECH_PARAM, DANGEROUS_CONTENT_PARAM, SEXUALLY_EXPLICIT_PARAM)

}