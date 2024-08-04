package com.aboe.trivilauncher.presentation.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aboe.trivilauncher.R
import com.aboe.trivilauncher.ui.theme.TriviLauncherTheme

@Composable
fun PillLabel(
    text: String,
    icon: ImageVector,
    modifier: Modifier = Modifier
) {
    var height by remember { mutableStateOf(0.dp) }

    Box(
        modifier = modifier
            .onSizeChanged {
                height = it.height.dp
            }
            .clip(RoundedCornerShape(height / 2))
            .background(Color.Black.copy(alpha = .05f))
            .padding(8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(imageVector = icon, contentDescription = "Icon", modifier.size(16.dp))
            Spacer(modifier = Modifier.width(4.dp))
            Text(text = text, style = TextStyle(fontSize = 10.sp))
            Spacer(modifier = Modifier.width(4.dp))
        }
    }
}

@Preview
@Composable
fun PillLabelPreview() {
    TriviLauncherTheme {
        PillLabel(
            text = "Label",
            icon = ImageVector.vectorResource(id = R.drawable.outline_spotlight_48)
        )
    }
}
