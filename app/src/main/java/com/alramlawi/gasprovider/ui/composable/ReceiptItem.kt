package com.alramlawi.gasprovider.ui.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alramlawi.gasprovider.R
import com.alramlawi.gasprovider.data.local.room.entity.ReceiptEntity
import com.alramlawi.gasprovider.utils.localString

@Composable
fun ReceiptItem(
    receiptEntity: ReceiptEntity,
    editable: Boolean,
    onEdit: () -> Unit
) {

    Surface(
        modifier = Modifier
            .padding(bottom = 4.dp)
            .fillMaxWidth(),
        elevation = 2.dp,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colors.surface)
                .padding(vertical = 8.dp)
                .padding(start = 8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Row(Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                
                Column {
                    PropertyItem(title = receiptEntity.customerName)
                    Text(
                        text = receiptEntity.date.localString,
                        style = MaterialTheme.typography.caption,
                        color = MaterialTheme.colors.onBackground.copy(0.7f),
                        maxLines = 1,
                    )
                }

                if (editable) {
                    IconButton(onClick = onEdit) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "",
                            tint = MaterialTheme.colors.primary
                        )
                    }
                }

            }

            Row(
                Modifier.fillMaxWidth().padding(end = 8.dp), horizontalArrangement = Arrangement.SpaceBetween) {

                PropertyItem(
                    icon = R.drawable.ic_lequid, content = stringResource(
                        R.string.receipt_unit, receiptEntity.amount
                    )
                )

                PropertyItem(
                    content = stringResource(
                        R.string.receipt_currency, receiptEntity.cashPayment
                    ),
                    color = MaterialTheme.colors.primary
                )

                PropertyItem(
                    content = stringResource(
                        R.string.receipt_currency, receiptEntity.remainingPayment
                    ),
                    color = MaterialTheme.colors.error
                )
            }
        }
    }


}


@Preview
@Composable
fun PreviewReceiptItem() {
    ReceiptItem(
        receiptEntity = ReceiptEntity(
            customerId = "123",
            customerName = "Majd Alramlawi",
            amount = 5.5,
            cashPayment = 50.0,
            remainingPayment = 5.0),
        editable = true
    ) {

    }
}