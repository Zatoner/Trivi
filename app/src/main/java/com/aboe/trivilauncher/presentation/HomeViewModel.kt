package com.aboe.trivilauncher.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aboe.trivilauncher.BuildConfig
import com.aboe.trivilauncher.domain.use_case.get_gemini_prompt.GetGeminiPrompt
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getGeminiPrompt: GetGeminiPrompt
) : ViewModel() {

    private val generativeModel = GenerativeModel(
        modelName = BuildConfig.modelName,
        apiKey = BuildConfig.geminiKey
    )

    init {
        println("HomeViewModel init")
        viewModelScope.launch {
            val prompt = getGeminiPrompt("Give me a quick status update, let me know if I need to do anything according ot my context (like notifications) and any apps that I should open right now. go into great detail")
            // print number of tokens
            println(prompt)
            println("Number of tokens: ${getNumberOfWords(prompt)}")

            val response = generativeModel.generateContent(
                content {
                    text(prompt)
                }
            )

            println(response.text)
        }
    }

    fun getNumberOfWords(text: String): Int {
        return text.trim().split("\\s+".toRegex()).size
    }

}