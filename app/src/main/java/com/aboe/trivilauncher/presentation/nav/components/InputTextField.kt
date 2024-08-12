package com.aboe.trivilauncher.presentation.nav.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextAlign

@OptIn(ExperimentalMaterial3Api::class)
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
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color.Black.copy(alpha = 0.4f),
            unfocusedBorderColor = Color.Black.copy(alpha = 0.4f),
            cursorColor = Color.Black.copy(alpha = 0.4f),
        ),
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.Sentences,
            autoCorrectEnabled = true,
            imeAction = ImeAction.Done
        ),
        textStyle = TextStyle(textAlign = TextAlign.Start),
        onValueChange = { input ->
            onValueChange(input)
        },
        keyboardActions = KeyboardActions(onDone = {
            onDoneAction()
        })
    )
}