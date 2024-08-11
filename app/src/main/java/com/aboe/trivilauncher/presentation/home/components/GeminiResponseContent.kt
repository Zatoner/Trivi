package com.aboe.trivilauncher.presentation.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.aboe.trivilauncher.domain.model.GeminiItem

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun GeminiResponseContent(
    modifier: Modifier = Modifier,
    data: GeminiItem,
    textAnimationCallback: () -> Unit = {},
    onAppClick: (String) -> Unit = {}
) {
    Column(modifier = modifier) {
        TypingText(
            modifier = Modifier
                .fillMaxWidth(),
            text = data.response,
            animate = !data.hasAnimated,
            animationCallback = {
                textAnimationCallback()
            }
        )

        if (data.hasAnimated && data.apps.isNotEmpty()) {
            Spacer(modifier = Modifier.height(8.dp))

            FlowRow (
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ){
                data.apps.forEachIndexed { index, app ->
                    AppPill(
                        appInfo = app,
                        animate = true,
                        delay = index * 250,
                        onClick = {
                            onAppClick(app.packageName)
                        }
                    )
                }
            }
        }
    }
}