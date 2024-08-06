package com.aboe.trivilauncher.presentation.home.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.drawable.toBitmap
import com.aboe.trivilauncher.R
import com.aboe.trivilauncher.domain.model.CompactAppInfo
import com.aboe.trivilauncher.ui.theme.TriviLauncherTheme
import kotlinx.coroutines.delay

@Composable
fun AppPill(
    modifier: Modifier = Modifier,
    appInfo: CompactAppInfo,
    onClick: () -> Unit,
    animate: Boolean = false,
    delay: Int = 0
) {
    var visible by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = animate) {
        if (animate) {
            delay(delay.toLong())
            visible = true
        }
    }

    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(animationSpec = tween(durationMillis = 700)),
        exit = fadeOut(animationSpec = tween(durationMillis = 700))
    ) {
        Box(
            modifier = modifier
                .border(
                    width = 1.dp,
                    color = Color.Black.copy(alpha = 0.2f),
                    shape = RoundedCornerShape(50)
                )
                .clip(RoundedCornerShape(50))
                .clickable {
                    onClick()
                }.padding(10.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    bitmap = appInfo.icon.toBitmap().asImageBitmap(),
                    contentDescription = appInfo.label,
                    modifier = Modifier.size(28.dp)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(text = appInfo.label, style = TextStyle(fontSize = 16.sp))

                Spacer(modifier = Modifier.width(4.dp))
            }
        }
    }
}

@Preview
@Composable
fun AppPillPreview() {
    TriviLauncherTheme { // Replace with your theme if different
        AppPill(
            appInfo = CompactAppInfo(
                label = "Test App",
                packageName = "com.test.app",
                icon = LocalContext.current.getDrawable(R.drawable.ic_launcher_foreground)!! // Replace with your drawable
            ),
            onClick ={ /* Handle click */ }
        )
    }
}

