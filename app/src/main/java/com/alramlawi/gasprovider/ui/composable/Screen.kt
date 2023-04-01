package com.alramlawi.gasprovider.ui.composable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.alramlawi.gasprovider.ui.composable.dialog.ErrorDialog
import com.alramlawi.gasprovider.ui.theme.GasProviderTheme

@Composable
fun Screen(
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    error: Pair<String, (() -> Unit)>? = null,
    content: @Composable BoxScope.() -> Unit,
) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            content()
        }

        if(isLoading) {
            LoadingItem()
        }

        error?.run {
            if(error.first.isNotEmpty()) {
                ErrorDialog(
                    message = error.first,
                    onDismiss = error.second
                )
            }
        }
    }
}


@Preview
@Composable
fun PreviewScreen() {
    GasProviderTheme {
        Screen(isLoading = true) {
            Text(text = "Hello world!", style = MaterialTheme.typography.h4)
        }
    }
}