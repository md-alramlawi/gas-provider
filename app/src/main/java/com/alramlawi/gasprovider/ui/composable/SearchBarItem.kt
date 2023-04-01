package com.alramlawi.gasprovider.ui.composable

import androidx.compose.foundation.border
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SearchBarItem(
    modifier: Modifier = Modifier,
    query: String,
    placeHolderText: String,
    onQueryChanged: (String) -> Unit,
) {
    val color = MaterialTheme.colors.onBackground

    TextField(
        modifier = modifier
            .clip(MaterialTheme.shapes.large)
            .border(
                width = 2.dp,
                color = MaterialTheme.colors.background,
                shape = MaterialTheme.shapes.large
            ),
        value = query,
        onValueChange = onQueryChanged,
        placeholder = {
            Text(
                text = placeHolderText,
                color = color.copy(0.5f)
            )
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search, contentDescription = placeHolderText,
                tint = color.copy(0.5f)
            )
        },
        colors = TextFieldDefaults.textFieldColors(backgroundColor = MaterialTheme.colors.background),
        singleLine = true,
    )
}

@Preview
@Composable
fun PreviewSearchParItem() {
    SearchBarItem(placeHolderText = "Search", query = "", onQueryChanged = {})
}