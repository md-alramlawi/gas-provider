package com.alramlawi.gasprovider.ui.composable

import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SimpleTextField(
    modifier : Modifier = Modifier,
    isError: Boolean,
    label: String,
    value: String,
    keyboardType : KeyboardType = KeyboardType.Text,
    onValueChanged: (String) -> Unit
) {
    OutlinedTextField(
        modifier = modifier.heightIn(50.dp),
        isError = isError,
        value = value,
        onValueChange = onValueChanged,
        label = { Text(text = label, maxLines = 1) },
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
    )
}


@Preview
@Composable
fun PreviewSimpleTextField() {
    SimpleTextField(
        modifier = Modifier.padding(8.dp),
        isError = false,
        label = "username",
        value = "Majd",
        onValueChanged = {}
    )
}