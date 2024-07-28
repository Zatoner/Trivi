package com.aboe.trivilauncher.presentation.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aboe.trivilauncher.common.Resource
import com.aboe.trivilauncher.domain.use_case.get_weather_widget.GetWeatherWidgetUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class HomeViewModel  @Inject constructor(
    private val getWeatherWidgetUseCase: GetWeatherWidgetUseCase
) : ViewModel() {

//    private val generativeModel = GenerativeModel(
//        modelName = Constants.MODEL_NAME,
//        apiKey = BuildConfig.geminiKey,
//        generationConfig = Constants.GEMINI_CONFIG,
//        safetySettings = Constants.SAFETY_SETTINGS
//    )

//    viewModelScope.launch {
//        withContext(Dispatchers.IO) {
//            val prompt = getGeminiPrompt()
//            println(prompt)
//
//            val response = generativeModel.generateContent(
//                content {
//                    text(prompt)
//                }
//            )
//            println(response.text)
//        }
//    }

    private var weatherJob: Job? = null

    private val _state = mutableStateOf(HomeState())
    val state: State<HomeState> = _state

    private val _eventFlow = MutableSharedFlow<HomeUIEvent>()
    val eventFlow = _eventFlow

//    init {
//        updateContents()
//    }

    fun updateContents() {
        println("Home on resume")

        _state.value = _state.value.copy(currentDateDate = getFormattedDate())
        getWeatherWidget()
    }

    private fun getWeatherWidget() {
        weatherJob?.let { job ->
            job.cancel()

            _state.value = _state.value.copy(
                weatherItem = null,
                isWeatherLoading = false
            )
        }

        weatherJob = getWeatherWidgetUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        weatherItem = result.data,
                        isWeatherLoading = false
                    )
                }
                is Resource.Loading -> {
                    _state.value = _state.value.copy(
                        isWeatherLoading = true
                    )
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        isWeatherLoading = false
                    )

                    result.message?.let { message ->
                        _eventFlow.emit(HomeUIEvent.ShowSnackbar(message))
                    }
                }
            }

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