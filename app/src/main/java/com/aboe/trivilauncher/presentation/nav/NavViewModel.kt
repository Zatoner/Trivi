package com.aboe.trivilauncher.presentation.nav

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class NavViewModel : ViewModel() {

    private val _screenState = mutableStateOf(ScreenState.HOME)
    val screenState: State<ScreenState> = _screenState

    private val _geminiText = MutableSharedFlow<String>(replay = 1)
    val geminiText = _geminiText

    fun setGeminiText(text: String) {
        viewModelScope.launch {
            _geminiText.emit(text)
        }
    }

    // replace by navcontroller routes instead
    fun setScreenState(state: ScreenState) {
        _screenState.value = state
    }

}