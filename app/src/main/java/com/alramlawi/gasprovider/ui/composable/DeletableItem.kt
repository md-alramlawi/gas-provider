package com.alramlawi.gasprovider.ui.composable

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alramlawi.gasprovider.R


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun <T> DeletableItem(
    enable: Boolean,
    item: T,
    content: @Composable RowScope.() -> Unit,
    onDelete: (T) -> Unit
) {

    val currentItem by rememberUpdatedState(item)
    var showDeletionDialog by remember { mutableStateOf(false) }
    val dismissState = rememberDismissState(confirmStateChange = { dismissValue ->

        val dismissed = dismissValue == DismissValue.DismissedToStart
        if (dismissed) {
            showDeletionDialog = true
        }
        false
    })

    SwipeToDismiss(
        state = dismissState,
        directions = if(enable) setOf(DismissDirection.EndToStart) else setOf(),
        dismissThresholds = { FractionalThreshold(1f) },
        background = {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.CenterEnd

            ) {
                Icon(
                    modifier = Modifier
                        .padding(end = 16.dp)
                        .size(32.dp),
                    imageVector = Icons.Default.Delete,
                    tint = MaterialTheme.colors.error,
                    contentDescription = ""
                )
            }
        },
        dismissContent = { content.invoke(this) }
    )

    if (showDeletionDialog) {
        ConfirmationDialog(
            title = stringResource(id = R.string.dialog_are_you_sure),
            details = stringResource(id = R.string.dialog_delete_item_details),
            onDismiss = {
                showDeletionDialog = false
            },
            onConfirm = {
                showDeletionDialog = false
                onDelete(currentItem)
            }
        )
    }

}

@Composable
fun ConfirmationDialog(
    title: String,
    details: String,
    onDismiss: () -> Unit,
    onConfirm:() -> Unit
) {

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = title,
                color = MaterialTheme.colors.onBackground,
                style = MaterialTheme.typography.subtitle1,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = details,
                color = MaterialTheme.colors.onBackground,
                style = MaterialTheme.typography.body1,
            )
        },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(
                    text = stringResource(id = R.string.dialog_yes),
                    color = MaterialTheme.colors.primary
                )
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(
                    text = stringResource(id = R.string.dialog_cancel),
                    color = MaterialTheme.colors.onBackground
                )
            }
        },
        shape = MaterialTheme.shapes.medium
    )
}


@Preview
@Composable
fun PreviewConfirmationDialog() {
    ConfirmationDialog(
        title = stringResource(id = R.string.dialog_are_you_sure),
        details = stringResource(id = R.string.dialog_delete_item_details),
        onDismiss = {},
        onConfirm = {}
    )
}