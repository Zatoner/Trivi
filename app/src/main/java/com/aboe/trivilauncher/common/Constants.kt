package com.aboe.trivilauncher.common

import com.google.ai.client.generativeai.type.BlockThreshold
import com.google.ai.client.generativeai.type.HarmCategory
import com.google.ai.client.generativeai.type.SafetySetting
import com.google.ai.client.generativeai.type.generationConfig

object Constants {

//    const val SYSTEM_PROMPT = """
//    You are a friend, human-like, and minimalistic personal assistant integrated into a launcher
//    designed to remove friction when using their phone. Understand the context of their conversations and provide detailed
//    information when necessary. Use plain text without formatting. Prioritize the most important
//    information first. Address the user directly, like you're having a conversation. DO NOT HALLUCINATE.
//
//    Your response must be in the following format (separator: " END "):
//    response text END App1,App2,App3
//
//    you can return up to a maximum of 3 apps that the user HAS INSTALLED, these apps you suggest should follow
//    the context of your response/conversation.
//"""
//
//    const val DEFAULT_PROMPT = """
//    Provide a concise update using the given context, aiming for 5-6 sentences.
//    This update will be displayed on the home screen to help reduce screen time, so prioritize the
//    most important and recent information. Summarize key conversations and updates, avoiding mundane
//    details like device status. Consider the time of day: if it’s the end of the day, provide a
//    summary and suggest activities for tomorrow; if it’s the start of the day, suggest relevant
//    actions. If there’s little to report, share interesting facts or other relevant information.
//    Avoid mentioning the time, date, and current weather (weather forecast is fine), as the user is already aware.
//    Do not ask follow up questions.
//"""

    const val SYSTEM_PROMPT = """
    You are a friend, human-like, minimalistic personal assistant integrated into an Android launcher to reduce phone use friction. 
    Understand the context and provide detailed information when necessary. Use plain text, no formatting. Prioritize 
    the most important information first. Address the user directly. DO NOT HALLUCINATE. Try to ignore repetitive content.
    Please take in time, its very important to stay coherent.
    
    RESPONSE FORMAT (separator: "-END-"):
    response text-END-App1,App2,App3
    
    Suggest up to 3 installed apps relevant to your response, don't be afraid of suggesting less than 3 apps.
"""

    const val DEFAULT_PROMPT = """
    Provide a concise update in roughly 5 sentences based on the context. This update will be on the home 
    screen to reduce screen time, so prioritize recent and important information. Summarize key 
    conversations and updates, avoiding mundane details. Consider the time of day: at the end of 
    the day, provide a summary and suggest tomorrow's activities; at the start of the day, 
    suggest relevant actions. If little to report, share interesting facts or relevant info. 
    Avoid mentioning time, date, and current weather (forecast is fine). Do not ask follow-up questions.
"""

    const val MAX_NOTIFICATION_AGE_HOURS = 12

    const val MAX_WEATHER_FORECAST_ITEMS = 8

    const val MAX_APP_USAGE_AGE_DAYS = 7

    const val MIN_APP_USAGE_TIME_MINUTES = 1

    const val MODEL_NAME = "gemini-1.5-pro"

    val GEMINI_CONFIG = generationConfig {
        temperature = 0.8f
        topK = 20
        topP = 0.95f
        maxOutputTokens = 256
    }

    private val HARASSMENT_PARAM = SafetySetting(HarmCategory.HARASSMENT, BlockThreshold.NONE)
    private val HATE_SPEECH_PARAM = SafetySetting(HarmCategory.HATE_SPEECH, BlockThreshold.NONE)
    private val DANGEROUS_CONTENT_PARAM = SafetySetting(HarmCategory.DANGEROUS_CONTENT, BlockThreshold.NONE)
    private val SEXUALLY_EXPLICIT_PARAM = SafetySetting(HarmCategory.SEXUALLY_EXPLICIT, BlockThreshold.NONE)
    val SAFETY_SETTINGS = listOf(HARASSMENT_PARAM, HATE_SPEECH_PARAM, DANGEROUS_CONTENT_PARAM, SEXUALLY_EXPLICIT_PARAM)

}