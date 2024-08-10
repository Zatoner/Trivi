package com.aboe.trivilauncher.presentation.gemini

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aboe.trivilauncher.common.Resource
import com.aboe.trivilauncher.domain.use_case.get_gemini_prompt.GetGeminiPromptUseCase
import com.aboe.trivilauncher.domain.use_case.get_gemini_response.GetGeminiResponseUseCase
import com.aboe.trivilauncher.presentation.apps.ChatItem
import com.google.ai.client.generativeai.type.Content
import com.google.ai.client.generativeai.type.content
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GeminiViewModel @Inject constructor(
    private val getGeminiPrompt: GetGeminiPromptUseCase,
    private val getGeminiResponse: GetGeminiResponseUseCase
) : ViewModel() {

    private var geminiJob: Job? = null

    private val _chatState = mutableStateOf<List<ChatItem>>(emptyList())
    val chatState: State<List<ChatItem>> = _chatState

    private val chatHistory: MutableList<Content> = mutableListOf()

    init {
        println("GeminiViewModel init")
        // add widget content in chat history using data store
    }

    suspend fun newRequest(request: String) {
        val lastItem = _chatState.value.lastOrNull()

        when {
            lastItem is ChatItem.UserRequest -> return
            lastItem is ChatItem.GeminiResponse && lastItem.response is Resource.Loading -> return
        }

        geminiJob?.cancel()

        _chatState.value += ChatItem.UserRequest(request)
        _chatState.value += ChatItem.GeminiResponse(Resource.Loading())
        val index = _chatState.value.lastIndex

        viewModelScope.launch(Dispatchers.IO) {
            val (prompt, history) = getGeminiPrompt(request)
            if (chatHistory.isEmpty()) {
                chatHistory += history
            }

            geminiJob = getGeminiResponse(prompt, chatHistory)
                .onEach { result ->
                    _chatState.value = _chatState.value.toMutableList().apply {
                        set(index, ChatItem.GeminiResponse(result))
                    }

                    if (result is Resource.Success) {
                        chatHistory += content(role = "user") { text(request) }
                        chatHistory += content(role = "model") {
                            text(result.data?.response ?: "Error getting Gemini response")
                        }
                    }
                }
                .launchIn(viewModelScope)

        }
    }

    fun completeGeminiAnimationState(index: Int) {
        val item = _chatState.value[index]

        if (item is ChatItem.GeminiResponse) {
            when (item.response) {
                is Resource.Success -> {
                    item.response.data?.let { data ->
                        _chatState.value = _chatState.value.toMutableList().apply {
                            set(index, ChatItem.GeminiResponse(Resource.Success(data.copy(hasAnimated = true))))
                        }
                    }
                }
                else -> Unit
            }

        }
    }

}