package com.alramlawi.gasprovider.ui.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alramlawi.gasprovider.R
import com.alramlawi.gasprovider.data.local.room.entity.CustomerEntity
import com.alramlawi.gasprovider.utils.localString

@Composable
fun CustomerItem(
    editable: Boolean,
    customerEntity: CustomerEntity,
    onClickItem: () -> Unit,
    onClickEdit: () -> Unit
) {
    Column(

        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClickItem() }
            .background(MaterialTheme.colors.surface)
    ) {
        Divider(modifier = Modifier.fillMaxWidth())

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .padding(start = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "",
                tint = MaterialTheme.colors.onBackground.copy(0.6f)
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 12.dp)
                    .align(Alignment.CenterVertically)
            ) {
                Text(
                    text = "${customerEntity.firstName} ${customerEntity.lastName}",
                    style = MaterialTheme.typography.h6,
                    color = MaterialTheme.colors.onBackground
                )


                Text(
                    text = stringResource(id = R.string.customer_item_last_activity, customerEntity.lastUpdate.localString),
                    style = MaterialTheme.typography.caption,
                    color = MaterialTheme.colors.onBackground.copy(0.7f),
                    maxLines = 1,
                )
            }

            if (editable) {
                IconButton(onClick = onClickEdit) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "",
                        tint = MaterialTheme.colors.primary
                    )
                }
            }
        }

        Divider(modifier = Modifier.fillMaxWidth())
    }

}


@Preview
@Composable
fun PreviewCustomerItem() {
    CustomerItem(editable = true,
        customerEntity = CustomerEntity("", "Majd", "Alramlawi",  ""), {}, {})
}