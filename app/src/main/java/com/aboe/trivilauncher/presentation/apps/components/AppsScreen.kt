package com.aboe.trivilauncher.presentation.apps.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.aboe.trivilauncher.presentation.apps.AppsViewModel

@Composable
fun AppsScreen(
    viewModel: AppsViewModel = hiltViewModel()
) {
    val appsState by viewModel.appsState

    Box(modifier = Modifier
        .padding(horizontal = 16.dp)
        .fillMaxSize()) {

        LazyVerticalGrid(
            modifier = Modifier.fillMaxSize(),
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(6.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            items(
                count = appsState.size,
                key = { index -> appsState[index].packageName })
            { index ->
                AppCard(
                    appInfo = appsState[index],
                    animate = true,
                    onClick = {
                        viewModel.launchApp(appsState[index].packageName)
                    }
                )
            }
        }
    }
}