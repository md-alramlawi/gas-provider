package com.alramlawi.gasprovider.ui.composable.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.AlertDialog
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alramlawi.gasprovider.ui.theme.GasProviderTheme

@Composable
fun GasProviderAlertDialog(
    title: String,
    message: String,
    icon: @Composable () -> Unit = {},
    confirmButton: String? = null,
    dismissButton: String? = null,
    onConfirm: () -> Unit = {},
    onDismiss: () -> Unit = {}
) {

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Row(
                Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                icon.invoke()

                Text(
                    text = title,
                    style = MaterialTheme.typography.h6,
                    textAlign = TextAlign.Start
                )
            }
        },
        text = {
            Text(
                text = message,
                style = MaterialTheme.typography.subtitle2.copy(
                    letterSpacing = 0.5.sp
                ),
                textAlign = TextAlign.Start
            )

        },
        confirmButton = {
            confirmButton?.let {
                TextButton(onClick = onConfirm) {
                    Text(
                        text = it,
                        color = MaterialTheme.colors.primary,
                        style = MaterialTheme.typography.subtitle1
                    )
                }
            }
        },
        dismissButton = {
            dismissButton?.let {
                TextButton(onClick = onDismiss) {
                    Text(
                        text = it,
                        color = MaterialTheme.colors.primary,
                        style = MaterialTheme.typography.subtitle1
                    )
                }
            }
        },
        shape = MaterialTheme.shapes.medium
    )
}


@Preview
@Composable
private fun PreviewAmanoAlertDialog() {
    GasProviderTheme {
        GasProviderAlertDialog(
            title = "Some title",
            message = "Some message from amano alert dialog.",
            confirmButton = "ok",
            dismissButton = "cancel"
        )
    }
}