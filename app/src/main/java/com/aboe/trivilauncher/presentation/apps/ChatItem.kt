package com.aboe.trivilauncher.presentation.apps

import com.aboe.trivilauncher.common.Resource
import com.aboe.trivilauncher.domain.model.GeminiItem

sealed class ChatItem {
    data class UserRequest(val request: String) : ChatItem()
    data class GeminiResponse(val response: Resource<GeminiItem>) : ChatItem()
}