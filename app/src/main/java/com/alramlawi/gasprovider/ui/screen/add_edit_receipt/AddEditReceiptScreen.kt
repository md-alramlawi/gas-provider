package com.alramlawi.gasprovider.ui.screen.add_edit_receipt

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.alramlawi.gasprovider.R
import com.alramlawi.gasprovider.data.local.room.entity.CustomerEntity
import com.alramlawi.gasprovider.ui.composable.AutoCompleteTextField
import com.alramlawi.gasprovider.ui.composable.Screen
import com.alramlawi.gasprovider.ui.composable.SimpleTextField

@Composable
fun AddEditReceiptScreen(
    navController: NavController,
    viewModel: AddEditReceiptViewModel = hiltViewModel()
) {

    val context = LocalContext.current
    val state by viewModel.state.collectAsState()

    Screen(isLoading = state.loading) {

        AddEditReceiptContent(
            isNewReceipt = state.receipt == null,
            customers = state.customers,
            customer = state.fullName to state.customerError,
            amount = state.amount to state.amountError,
            cashPayment = state.cashPayment to state.cashError,
            remainingPayment = state.remainingPayment to state.remindingError,
            onPickCustomer = viewModel::selectCustomer,
            onNameChanged = viewModel::onNameChanged,
            onAmountChanged = viewModel::onAmountChanged,
            onCashPaymentChanged = viewModel::onCashPaymentChanged,
            onRemainingPaymentChanged = viewModel::onRemindingPaymentChanged,
            onClickSave = viewModel::saveReceipt
        )
    }


    LaunchedEffect(key1 = state.saveComplete) {
        if (state.saveComplete) {
            Toast.makeText(context, context.getText(R.string.item_saved), Toast.LENGTH_SHORT).show()

            if (state.receipt != null) {
                navController.navigateUp()
            }
        }
    }
}


@Composable
fun AddEditReceiptContent(
    isNewReceipt: Boolean,
    customers: List<CustomerEntity>,
    customer: Pair<String, Boolean>,
    amount: Pair<String, Boolean>,
    cashPayment: Pair<String, Boolean>,
    remainingPayment: Pair<String, Boolean>,
    onPickCustomer: (CustomerEntity?) -> Unit,
    onNameChanged: (String) -> Unit,
    onAmountChanged: (String) -> Unit,
    onCashPaymentChanged: (String) -> Unit,
    onRemainingPaymentChanged: (String) -> Unit,
    onClickSave: () -> Unit,
) {

    val title = if (isNewReceipt)
        stringResource(id = R.string.add_new_receipt_title)
    else
        stringResource(id = R.string.update_receipt_title)

    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.weight(0.1f))
        Text(
            text = title,
            style = MaterialTheme.typography.h6,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.weight(0.2f))


        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.TopCenter
        ) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 70.dp)
            ) {
                SimpleTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp),
                    value = amount.first,
                    isError = amount.second,
                    label = stringResource(id = R.string.receipt_amount_label),
                    onValueChanged = onAmountChanged
                )

                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    SimpleTextField(
                        modifier = Modifier.weight(1f),
                        value = cashPayment.first,
                        isError = cashPayment.second,
                        label = stringResource(id = R.string.receipt_cash_payment_label),
                        onValueChanged = onCashPaymentChanged
                    )

                    SimpleTextField(
                        modifier = Modifier.weight(1f),
                        value = remainingPayment.first,
                        isError = remainingPayment.second,
                        label = stringResource(id = R.string.receipt_remaining_payment_label),
                        onValueChanged = onRemainingPaymentChanged
                    )

                }
            }

            AutoCompleteTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp),
                value = customer.first,
                label = stringResource(id = R.string.receipt_full_name_label),
                isError = customer.second,
                onQueryChanged = onNameChanged,
                predictions = customers,
                onItemClick = onPickCustomer
            ) {
                Text(
                    modifier = Modifier,
                    text = it.fullName,
                    style = MaterialTheme.typography.caption,
                    color = MaterialTheme.colors.surface
                )
            }
        }


        Spacer(modifier = Modifier.weight(0.2f))

        Button(
            modifier = Modifier.fillMaxWidth(0.5f),
            onClick = onClickSave
        ) {
            Text(text = stringResource(id = R.string.button_save_title))
        }

        Spacer(modifier = Modifier.weight(0.1f))
    }


}

@Preview
@Composable
fun PreviewAddEditCustomerContent() {
    AddEditReceiptContent(
        isNewReceipt = true,
        customers = emptyList(),
        customer = "" to false,
        amount = "50.0" to false,
        cashPayment = "22.0" to false,
        remainingPayment = "2.0" to false,
        onPickCustomer = {},
        onNameChanged = {},
        onAmountChanged = {},
        onCashPaymentChanged = {},
        onRemainingPaymentChanged = {}
    ) {

    }
}