package com.aboe.trivilauncher.presentation.apps.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmap
import com.aboe.trivilauncher.domain.model.CompactAppInfo

@Composable
fun AppCard(
    modifier: Modifier = Modifier,
    appInfo: CompactAppInfo,
    onClick: () -> Unit,
    animate: Boolean = false
) {
    val animatedVisibility = remember { Animatable(initialValue = 0f) }

    LaunchedEffect(key1 = animate) {
        if (animate) {
            animatedVisibility.animateTo(
                targetValue = 1f,
                animationSpec = tween(durationMillis = 300)
            )
        }
    }

    Box(
        // move to child box
        modifier = modifier.alpha(animatedVisibility.value)
    ) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .clip(RoundedCornerShape(12.dp))
                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.5f))
                .clickable { onClick() }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    bitmap = appInfo.icon.toBitmap().asImageBitmap(),
                    contentDescription = appInfo.label,
                    modifier = Modifier.size(80.dp)
                )
                Spacer(modifier = Modifier.height(24.dp))
                Text(text = appInfo.label, style = MaterialTheme.typography.labelLarge, textAlign = TextAlign.Center)
            }
        }
    }
}