package com.aboe.trivilauncher.presentation.apps

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aboe.trivilauncher.domain.model.CompactAppInfo
import com.aboe.trivilauncher.domain.use_case.get_apps.GetAppsUseCase
import com.aboe.trivilauncher.domain.use_case.launch_app.LaunchAppUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AppsViewModel @Inject constructor(
    private val getApps: GetAppsUseCase,
    private val launchAppIntent: LaunchAppUseCase
) : ViewModel() {

    private val _appsState = mutableStateOf<List<CompactAppInfo>>(emptyList())
    val appsState: State<List<CompactAppInfo>> = _appsState

    init {
        viewModelScope.launch {
            withContext(Dispatchers.Main) {
                _appsState.value = getApps()
            }
        }
    }

    fun launchApp(packageName: String) {
        if (!launchAppIntent(packageName)) {
            Log.e("AppsViewModel", "Couldn't launch app")
        }
    }
}