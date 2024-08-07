package com.aboe.trivilauncher.presentation.nav

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class NavViewModel : ViewModel() {

    private val _screenState = mutableStateOf(ScreenState.HOME)
    val screenState: State<ScreenState> = _screenState

    fun setScreenState(state: ScreenState) {
        _screenState.value = state
    }

}