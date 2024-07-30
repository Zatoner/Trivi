package com.aboe.trivilauncher.presentation.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aboe.trivilauncher.common.Resource
import com.aboe.trivilauncher.domain.use_case.get_gemini_prompt.GetGeminiPromptUseCase
import com.aboe.trivilauncher.domain.use_case.get_gemini_response.GetGeminiResponseUseCase
import com.aboe.trivilauncher.domain.use_case.get_weather_widget.GetWeatherWidgetUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getWeatherWidgetUseCase: GetWeatherWidgetUseCase,
    private val getGeminiPromptUseCase: GetGeminiPromptUseCase,
    private val getGeminiResponseUseCase: GetGeminiResponseUseCase
) : ViewModel() {

    private var lastUpdate: Long = 0
    private val updateThreshold = 5 * 60 * 1000

    private var weatherJob: Job? = null
    private var geminiJob: Job? = null

    private val _state = mutableStateOf(HomeState())
    val state: State<HomeState> = _state

    private val _eventFlow = MutableSharedFlow<HomeUIEvent>()
    val eventFlow = _eventFlow

    fun updateContents() {
        val currentTime = System.currentTimeMillis()
        val timeSinceLastUpdate = currentTime - lastUpdate

        _state.value = _state.value.copy(currentDateDate = getFormattedDate())

        if (timeSinceLastUpdate > updateThreshold) {
            lastUpdate = currentTime

            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    updateWeatherWidget()
                    updateGeminiResponse()
                }
            }
        }
    }

    fun resetLastUpdate() {
        lastUpdate = 0
    }

    private suspend fun updateGeminiResponse() {
        geminiJob?.let { job ->
            job.cancel()

            _state.value = _state.value.copy(
                geminiItem = null,
                isGeminiLoading = false
            )
        }

        val prompt = getGeminiPromptUseCase()

        geminiJob = getGeminiResponseUseCase(prompt)
            .onEach { result ->
                val newState = when (result) {
                    is Resource.Success -> _state.value.copy(
                        geminiItem = result.data,
                        isGeminiLoading = false
                    )

                    is Resource.Loading -> _state.value.copy(
                        geminiItem = result.data,
                        isGeminiLoading = true
                    )

                    is Resource.Error -> {
                        result.message?.let { message ->
                            _eventFlow.emit(HomeUIEvent.ShowSnackbar(message))
                        }

                        _state.value.copy(
                            geminiItem = null,
                            isGeminiLoading = false
                        )
                    }
                }
                _state.value = newState
            }.launchIn(viewModelScope)
    }

    private suspend fun updateWeatherWidget() {
        weatherJob?.let { job ->
            job.cancel()

            _state.value = _state.value.copy(
                weatherItem = null,
                isWeatherLoading = false
            )
        }

        weatherJob = getWeatherWidgetUseCase()
            .onEach { result ->
                val newState = when (result) {
                    is Resource.Success -> _state.value.copy(
                        weatherItem = result.data,
                        isWeatherLoading = false
                    )

                    is Resource.Loading -> _state.value.copy(
                        isWeatherLoading = true
                    )

                    is Resource.Error -> {
                        result.message?.let { message ->
                            _eventFlow.emit(HomeUIEvent.ShowSnackbar(message))
                        }

                        _state.value.copy(
                            isWeatherLoading = false
                        )
                    }
                }
                _state.value = newState
            }.launchIn(viewModelScope)
    }

    private fun getFormattedDate(): String {
        val calendar = Calendar.getInstance()

        val dayOfWeek = SimpleDateFormat("EEEE", Locale.getDefault()).format(calendar.time)
        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)
        val dayOfMonthSuffix = getDayOfMonthSuffix(dayOfMonth)
        val month = SimpleDateFormat("MMMM", Locale.getDefault()).format(calendar.time)

        return "$dayOfWeek ${dayOfMonth}$dayOfMonthSuffix, $month"
    }

    private fun getDayOfMonthSuffix(day: Int): String {
        return when {
            day in 11..13 -> "th"
            day % 10 == 1 -> "st"
            day % 10 == 2 -> "nd"
            day % 10 == 3 -> "rd"
            else -> "th"
        }
    }

}