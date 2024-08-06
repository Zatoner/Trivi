package com.aboe.trivilauncher.presentation.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aboe.trivilauncher.common.Resource
import com.aboe.trivilauncher.domain.model.GeminiItem
import com.aboe.trivilauncher.domain.model.WeatherWidgetItem
import com.aboe.trivilauncher.domain.use_case.get_formatted_date.GetFormattedDateUseCase
import com.aboe.trivilauncher.domain.use_case.get_gemini_prompt.GetGeminiPromptUseCase
import com.aboe.trivilauncher.domain.use_case.get_gemini_response.GetGeminiResponseUseCase
import com.aboe.trivilauncher.domain.use_case.get_weather_widget.GetWeatherWidgetUseCase
import com.aboe.trivilauncher.domain.use_case.launch_app.LaunchAppUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getWeatherWidget: GetWeatherWidgetUseCase,
    private val getGeminiPrompt: GetGeminiPromptUseCase,
    private val getGeminiResponse: GetGeminiResponseUseCase,
    private val getFormattedDate: GetFormattedDateUseCase,
    private val launchAppIntent: LaunchAppUseCase
) : ViewModel() {

    private var lastUpdate: Long = 0
    private val updateThreshold = 10 * 60 * 1000

    private var weatherJob: Job? = null
    private var geminiJob: Job? = null

    private val _dateState = mutableStateOf("")
    val dateState: State<String> = _dateState

    private val _weatherState = mutableStateOf<Resource<WeatherWidgetItem>>(Resource.Loading())
    val weatherState: State<Resource<WeatherWidgetItem>> = _weatherState

    private val _geminiState = mutableStateOf<Resource<GeminiItem>>(Resource.Loading())
    val geminiState: State<Resource<GeminiItem>> = _geminiState

    private val _eventFlow = MutableSharedFlow<HomeUIEvent>()
    val eventFlow = _eventFlow

    fun updateContents() {
        val currentTime = System.currentTimeMillis()
        val timeSinceLastUpdate = currentTime - lastUpdate

        _dateState.value = getFormattedDate()

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

    fun completeGeminiAnimationState() {
        when (_geminiState.value) {
            is Resource.Success -> {
                _geminiState.value.data?.let {
                    _geminiState.value = Resource.Success(it.copy(hasAnimated = true))
                }
            }

            else -> Unit
        }
    }

    fun launchApp(packageName: String) {
        if (!launchAppIntent(packageName)) {
            viewModelScope.launch {
                _eventFlow.emit(HomeUIEvent.ShowSnackbar("Couldn't launch app"))
            }
        }
    }

    private suspend fun updateGeminiResponse() {
        geminiJob?.cancel()
        _geminiState.value = Resource.Loading()

        val prompt = getGeminiPrompt()

        // can be improved
        geminiJob = getGeminiResponse(prompt)
            .onEach { result ->
                _geminiState.value = when (result) {
                    is Resource.Success -> Resource.Success(result.data as GeminiItem)
                    is Resource.Loading -> Resource.Loading(result.data)
                    is Resource.Error -> {
                        result.message?.let { message ->
                            _eventFlow.emit(HomeUIEvent.ShowSnackbar(message))
                        }

                        Resource.Error("Couldn't get Gemini response")
                    }
                }
            }.launchIn(viewModelScope)
    }

    private suspend fun updateWeatherWidget() {
        weatherJob?.cancel()
        _weatherState.value = Resource.Loading()

        // can be improved
        weatherJob = getWeatherWidget()
            .onEach { result ->
                _weatherState.value = when (result) {
                    is Resource.Success -> Resource.Success(result.data as WeatherWidgetItem)
                    is Resource.Loading -> Resource.Loading()
                    is Resource.Error -> {
                        result.message?.let { message ->
                            _eventFlow.emit(HomeUIEvent.ShowSnackbar(message))
                        }

                       Resource.Error("No weather data")
                    }
                }
            }.launchIn(viewModelScope)
    }

}