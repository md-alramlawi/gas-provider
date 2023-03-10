package com.alramlawi.gasprovider.ui.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp

@Composable
fun <T> AutoCompleteTextField(
    modifier: Modifier = Modifier,
    value: String,
    label: String,
    isError: Boolean,
    onQueryChanged: (String) -> Unit = {},
    predictions: List<T>,
    onItemClick: (T) -> Unit = {},
    itemContent: @Composable (T) -> Unit = {}
) {

    val view = LocalView.current
    val lazyListState = rememberLazyListState()
    Column(Modifier.fillMaxWidth()) {

        SimpleTextField(
            modifier = modifier,
            isError = isError,
            label = label,
            value = value,
            onValueChanged = onQueryChanged
        )

        if (predictions.isNotEmpty()) {

        LazyColumn(
            state = lazyListState,
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .padding(top = 4.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(MaterialTheme.colors.onBackground)
        ) {

                items(predictions) { prediction ->
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .clickable {
                                view.clearFocus()
                                onItemClick(prediction)
                            }.padding(8.dp)
                    ) {
                        itemContent(prediction)
                    }
                }
            }
        }
    }
}
