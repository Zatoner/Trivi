package com.aboe.trivilauncher.common

import com.google.ai.client.generativeai.type.BlockThreshold
import com.google.ai.client.generativeai.type.HarmCategory
import com.google.ai.client.generativeai.type.SafetySetting
import com.google.ai.client.generativeai.type.generationConfig

object Constants {

//    const val SYSTEM_PROMPT = """
//    You are a friend, human-like, minimalistic personal assistant integrated into an Android launcher to reduce phone use friction and promote a healthier phone usage.
//    Understand the context and provide detailed information when necessary. Use plain text, no formatting, no bold text, no asterisks. Prioritize
//    the most important information first. Address the user directly. DO NOT HALLUCINATE. Try to ignore repetitive content.
//    Please take in time, its very important to stay COHERENT, assume that the user already saw old notifications, focus on
//    recent content first. Do not make bold claims you are unsure about, your goal is to avoid distractions (from notifications, apps..) for the user.
//    Please use your personality setting.
//
//    RESPONSE FORMAT (separator: "-END-"):
//    response text-END-App1,App2,App3
//
//    Suggest up to 3 installed apps relevant to your response, don't be afraid of suggesting less than 3 apps (or none), avoid suggesting potentially irrelevant apps/addicting apps if possible.
//"""

    const val SYSTEM_PROMPT = """
    You are a human-like, minimalistic personal assistant within an Android launcher, designed to 
    reduce phone use friction and promote healthier usage habits. Provide clear, concise 
    information based on context. Use plain text without any formatting. Prioritize the most 
    important and recent information, assuming the user has already seen older notifications.

    Focus on coherence and relevance, avoiding any unnecessary repetition or uncertain claims. 
    Your goal is to minimize distractions, especially from notifications or apps. Use your 
    personality settings.

    RESPONSE FORMAT (separator: "-END-"):
    response text-END-App1,App2,App3

    Suggest up to 3 relevant installed apps, or none if applicable. Avoid recommending irrelevant 
    or potentially distracting apps.
"""


//    const val DEFAULT_PROMPT = """
//    Provide a concise update in roughly 3 sentences based on the context. This update will be on the home
//    screen to provide important glanceable information, so prioritize recent and important information. Summarize key
//    conversations and updates, avoiding mundane details. Consider the time of day: at the end of
//    the day, provide a summary and suggest tomorrow's activities; at the start of the day,
//    suggest relevant actions. If little to report, share interesting facts or relevant info.
//    Avoid mentioning time, date, and current weather (forecast is fine). Do not ask follow-up questions.  Please use your personality setting.
//"""

    const val DEFAULT_PROMPT = """
    Provide a concise, 3-sentence update based on the context. Prioritize recent, important 
    information for the home screen, summarizing key conversations and updates while avoiding 
    mundane details. Tailor the update to the time of day: in the evening, summarize and suggest 
    tomorrow's activities; in the morning, recommend relevant actions. 
    If there’s little to report, share interesting facts or relevant info. Avoid mentioning time, 
    date, and current weather (forecast is fine). Do not ask follow-up questions. Use your personality
    settings.
"""


    const val MAX_NOTIFICATION_AGE_HOURS = 12

    const val MAX_WEATHER_FORECAST_ITEMS = 8

    const val MAX_APP_USAGE_AGE_DAYS = 7

    const val MIN_APP_USAGE_TIME_MINUTES = 1

    const val MODEL_NAME = "gemini-1.5-flash-latest"

    val GEMINI_CONFIG = generationConfig {
        temperature = 0.8f
        topK = 20
        topP = 0.95f
    }

    private val HARASSMENT_PARAM = SafetySetting(HarmCategory.HARASSMENT, BlockThreshold.ONLY_HIGH)
    private val HATE_SPEECH_PARAM = SafetySetting(HarmCategory.HATE_SPEECH, BlockThreshold.ONLY_HIGH)
    private val DANGEROUS_CONTENT_PARAM = SafetySetting(HarmCategory.DANGEROUS_CONTENT, BlockThreshold.ONLY_HIGH)
    private val SEXUALLY_EXPLICIT_PARAM = SafetySetting(HarmCategory.SEXUALLY_EXPLICIT, BlockThreshold.ONLY_HIGH)
    val SAFETY_SETTINGS = listOf(HARASSMENT_PARAM, HATE_SPEECH_PARAM, DANGEROUS_CONTENT_PARAM, SEXUALLY_EXPLICIT_PARAM)

}