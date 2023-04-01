package com.alramlawi.gasprovider.ui.composable.dialog

import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alramlawi.gasprovider.R
import com.alramlawi.gasprovider.ui.theme.GasProviderTheme

@Composable
fun ErrorDialog(
    message: String,
    onDismiss: () -> Unit
) {

    GasProviderAlertDialog(
        title = stringResource(id = R.string.alert_dialog_error_title),
        message = message,
        icon = {
            Icon(
                modifier = Modifier.size(24.dp),
                painter = painterResource(id = R.drawable.ic_error),
                contentDescription = message,
                tint = MaterialTheme.colors.error
            )
        },
        dismissButton = stringResource(id = R.string.alert_dialog_dismiss_button),
        onDismiss = onDismiss
    )
}


@Preview
@Composable
fun PreviewConfirmationDialog() {
    GasProviderTheme {
        ErrorDialog(message = "Something went wrong !!") {

        }
    }
}