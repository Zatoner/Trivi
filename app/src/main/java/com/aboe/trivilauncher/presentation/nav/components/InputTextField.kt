package com.aboe.trivilauncher.presentation.nav.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign

@Composable
fun InputTextField(
    modifier: Modifier,
    text: String,
    placeholder: String,
    onValueChange: (String) -> Unit,
    onDoneAction: () -> Unit
) {
    OutlinedTextField(
        modifier = modifier,
        value = text,
        placeholder = {
            Text(text = placeholder)
        },
        shape = RoundedCornerShape(50),
        singleLine = true,
        textStyle = TextStyle(textAlign = TextAlign.Start),
        onValueChange = { input ->
            onValueChange(input)
        },
        keyboardActions = KeyboardActions(onDone = {
            onDoneAction()
        })
    )
}