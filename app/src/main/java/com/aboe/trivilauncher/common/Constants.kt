package com.aboe.trivilauncher.common

import com.google.ai.client.generativeai.type.BlockThreshold
import com.google.ai.client.generativeai.type.HarmCategory
import com.google.ai.client.generativeai.type.SafetySetting
import com.google.ai.client.generativeai.type.generationConfig

object Constants {

    const val SYSTEM_PROMPT = """
    You are a friendly, human-like, and minimalistic personal assistant integrated into a launcher 
    designed to reduce screen time. Understand the context of my conversations and provide detailed
    information when necessary. Use plain text without formatting. Prioritize the most important 
    information first. Address the user directly, like you're having a conversation.
"""

    const val DEFAULT_PROMPT = """
    Provide a concise update using the context, aiming for 4-5 sentences. This update will be 
    displayed on the home screen to help reduce screen time, so focus on the most important and 
    recent information. Summarize key conversations and updates, and avoid mundane details like 
    device status. If there's little to report, share interesting facts about the user's location, 
    app usage, or other relevant information. Avoid mentioning the time, date, and current weather (weather forecast is fine) 
    as the user is already aware. Try and prevent app over usage and screen time to promote healthier lifestyles.
     Suggest 2-3 apps the user should open right now, listing only the app names.
"""
    const val MAX_NOTIFICATION_AGE_HOURS = 12

    const val MAX_WEATHER_FORECAST_ITEMS = 8

    const val MAX_APP_USAGE_AGE_DAYS = 7

    const val MIN_APP_USAGE_TIME_MINUTES = 1

    val MODEL_NAME = "gemini-1.5-flash"

    val GEMINI_CONFIG = generationConfig {
        temperature = 0.6f
        topK = 10
        topP = 0.9f
    }

    private val HARASSMENT_PARAM = SafetySetting(HarmCategory.HARASSMENT, BlockThreshold.NONE)
    private val HATE_SPEECH_PARAM = SafetySetting(HarmCategory.HATE_SPEECH, BlockThreshold.NONE)
    private val DANGEROUS_CONTENT_PARAM = SafetySetting(HarmCategory.DANGEROUS_CONTENT, BlockThreshold.NONE)
    private val SEXUALLY_EXPLICIT_PARAM = SafetySetting(HarmCategory.SEXUALLY_EXPLICIT, BlockThreshold.NONE)
    val SAFETY_SETTINGS = listOf(HARASSMENT_PARAM, HATE_SPEECH_PARAM, DANGEROUS_CONTENT_PARAM, SEXUALLY_EXPLICIT_PARAM)

}