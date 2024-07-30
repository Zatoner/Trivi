package com.aboe.trivilauncher.domain.use_case.get_gemini_response

import android.util.Log
import com.aboe.trivilauncher.BuildConfig
import com.aboe.trivilauncher.common.Constants
import com.aboe.trivilauncher.common.Resource
import com.aboe.trivilauncher.domain.model.GeminiItem
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.Content
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetGeminiResponseUseCase @Inject constructor() {

    val TAG = "GetGeminiResponseUseCase"

    private val generativeModel = GenerativeModel(
        modelName = Constants.MODEL_NAME,
        apiKey = BuildConfig.geminiKey,
        generationConfig = Constants.GEMINI_CONFIG,
        safetySettings = Constants.SAFETY_SETTINGS
    )

    suspend operator fun invoke(prompt: String, history: List<Content> = emptyList()) : Flow<Resource<GeminiItem>> = flow {
        try {
            emit(Resource.Loading())
            val chat = generativeModel.startChat(history = history)
            var result = GeminiItem("")

            chat.sendMessageStream(prompt).collect { textChunk ->
                result = result.copy(response = result.response + (textChunk.text ?: ""))
                emit(Resource.Loading(data = result))
            }

            // get apps
            //result = result.copy(apps = "")

            emit(Resource.Success(result))
            return@flow
        } catch(e: Exception) {
            Log.e(TAG, "Error getting Gemini response ${e.message}", e)
            emit(Resource.Error("Error getting Gemini response"))
        }
    }

}