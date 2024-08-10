package com.aboe.trivilauncher.presentation.gemini.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.aboe.trivilauncher.common.Resource
import com.aboe.trivilauncher.presentation.apps.ChatItem
import com.aboe.trivilauncher.presentation.gemini.GeminiViewModel
import com.aboe.trivilauncher.presentation.home.components.GeminiResponseContent
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collectLatest

@Composable
fun GeminiScreen(
    inputText: MutableSharedFlow<String>,
    viewModel: GeminiViewModel = hiltViewModel()
) {
    LaunchedEffect(inputText) {
        inputText.collectLatest { text ->
            viewModel.newRequest(text)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(16.dp))
            }

            items(viewModel.chatState.value.size) { index ->
                val item = viewModel.chatState.value[index]

                when (item) {
                    is ChatItem.GeminiResponse -> {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(16.dp))
                                .background(Color.Black.copy(alpha = 0.05f))
                                .padding(16.dp)
                        ) {
                            val response = item.response

                            when (response) {
                                is Resource.Success -> response.data?.let { data ->
                                    GeminiResponseContent(
                                        modifier = Modifier
                                            .fillMaxSize(),
                                        data = data,
                                        textAnimationCallback = {
                                            viewModel.completeGeminiAnimationState(index)
                                        },
                                        onAppClick = { packageName ->
                                            //
                                        }
                                    )
                                }
                                is Resource.Loading -> Text(text = "Generating...")
                                is Resource.Error -> Text(text = response.message ?: "Error :(")
                            }
                        }
                    }
                    is ChatItem.UserRequest -> Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(
                                width = 1.dp,
                                color = Color.Black.copy(alpha = 0.1f),
                                shape = RoundedCornerShape(16.dp)
                            )
                            .padding(16.dp)
                    ) {
                        Text(
                            modifier = Modifier.fillMaxSize(),
                            text = item.request,
                            textAlign = TextAlign.End
                        )
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}