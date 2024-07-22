package com.aboe.trivilauncher.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aboe.trivilauncher.BuildConfig
import com.aboe.trivilauncher.common.Constants
import com.aboe.trivilauncher.common.Resource
import com.aboe.trivilauncher.domain.use_case.get_gemini_prompt.GetGeminiPrompt
import com.aboe.trivilauncher.domain.use_case.get_weather_widget.GetWeatherWidgetUseCase
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getGeminiPrompt: GetGeminiPrompt,
    private val getWeatherWidgetUseCase: GetWeatherWidgetUseCase
) : ViewModel() {

    private val generativeModel = GenerativeModel(
        modelName = BuildConfig.modelName,
        apiKey = BuildConfig.geminiKey,
        generationConfig = Constants.GEMINI_CONFIG,
        safetySettings = Constants.SAFETY_SETTINGS
    )

    init {

        getWeatherWidgetUseCase().onEach { result ->
            when (result) {
                is Resource.Error -> println("Error ${result.message}")
                is Resource.Loading -> println("Loading ${result.message}")
                is Resource.Success -> println("Success ${result.data}")
            }

        }.launchIn(viewModelScope)

        viewModelScope.launch {
            val prompt = getGeminiPrompt()
            println(prompt)

            val response = generativeModel.generateContent(
                content {
                    text(prompt)
                }
            )
            println(response.text)
        }
    }

}