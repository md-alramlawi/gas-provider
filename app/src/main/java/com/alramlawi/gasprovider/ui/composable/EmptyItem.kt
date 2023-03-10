package com.alramlawi.gasprovider.ui.composable

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun EmptyItem() {
    LogoItem(tint = MaterialTheme.colors.onBackground.copy(0.3f))
}


@Preview
@Composable
fun PreviewEmptyItem() {
    EmptyItem()
}