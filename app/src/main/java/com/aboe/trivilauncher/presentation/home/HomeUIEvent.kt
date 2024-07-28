package com.aboe.trivilauncher.presentation.home

sealed class HomeUIEvent {
    data class ShowSnackbar(val message: String) : HomeUIEvent()
}