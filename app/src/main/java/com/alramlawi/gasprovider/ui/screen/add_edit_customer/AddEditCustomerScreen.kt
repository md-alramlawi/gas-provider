package com.alramlawi.gasprovider.ui.screen.add_edit_customer

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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.alramlawi.gasprovider.R
import com.alramlawi.gasprovider.data.local.room.entity.CustomerEntity
import com.alramlawi.gasprovider.ui.composable.Screen
import com.alramlawi.gasprovider.ui.composable.SimpleTextField
import com.alramlawi.gasprovider.ui.theme.GasProviderTheme

@Composable
fun AddEditCustomerScreen(
    navController: NavController,
    viewModel: AddEditCustomerViewModel = hiltViewModel()
) {

    val context = LocalContext.current
    val state by viewModel.state.collectAsState()

    Screen(isLoading = state.loading) {

        AddEditCustomerContent(
            isNewCustomer = state.isNewCustomer,
            customerEntity = state.customer,
            idError = state.idError,
            firstNameError = state.firstNameError,
            lastNameError = state.lastNameError,
            mobileError = state.mobileError,
            onIdChanged = viewModel::onIdNumberChanged,
            onFirstNameChanged = viewModel::onFirstNameChanged,
            onLastNameChanged = viewModel::onLastNameChanged,
            onMobileChanged = viewModel::onMobileChanged,
            onClickSave = viewModel::saveCustomer
        )
    }

    LaunchedEffect(key1 = state.saveComplete) {
        if (state.saveComplete) {
            Toast.makeText(context, context.getText(R.string.item_saved), Toast.LENGTH_SHORT).show()

            if (!state.isNewCustomer) {
                navController.navigateUp()
            }
        }
    }
}


@Composable
fun AddEditCustomerContent(
    isNewCustomer: Boolean,
    customerEntity: CustomerEntity,
    idError: Boolean,
    firstNameError: Boolean,
    lastNameError: Boolean,
    mobileError: Boolean,
    onIdChanged: (String) -> Unit,
    onFirstNameChanged: (String) -> Unit,
    onLastNameChanged: (String) -> Unit,
    onMobileChanged: (String) -> Unit,
    onClickSave: () -> Unit,
) {

    val title = if (isNewCustomer)
        stringResource(id = R.string.add_new_customer_title)
    else
        stringResource(id = R.string.update_customer_title)

    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.weight(0.1f))
        Text(
            text = title,
            style = MaterialTheme.typography.h5,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.weight(0.2f))

        SimpleTextField(
            modifier = Modifier.fillMaxWidth(),
            isError = idError,
            label = stringResource(id = R.string.id_number_title),
            value = customerEntity.idNumber,
            keyboardType = KeyboardType.Number,
            onValueChanged = onIdChanged
        )

        Row(
            Modifier
                .fillMaxWidth()
                .padding(top = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            SimpleTextField(
                modifier = Modifier.weight(1f),
                isError = firstNameError,
                label = stringResource(id = R.string.first_name_title),
                value = customerEntity.firstName,
                keyboardType = KeyboardType.Text,
                onValueChanged = onFirstNameChanged
            )


            SimpleTextField(
                modifier = Modifier.weight(1f),
                isError = lastNameError,
                label = stringResource(id = R.string.last_name_title),
                value = customerEntity.lastName,
                keyboardType = KeyboardType.Text,
                onValueChanged = onLastNameChanged
            )

        }


        SimpleTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp),
            isError = mobileError,
            label = stringResource(id = R.string.mobile_number_title),
            value = customerEntity.mobile,
            keyboardType = KeyboardType.Number,
            onValueChanged = onMobileChanged
        )


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
    GasProviderTheme {

        AddEditCustomerContent(
            isNewCustomer = true,
            customerEntity = CustomerEntity("", "", "", ""),
            idError = true,
            firstNameError = false,
            lastNameError = true,
            mobileError = false,
            onIdChanged = {},
            onFirstNameChanged = {},
            onLastNameChanged = {},
            onMobileChanged = {}
        ) {

        }
    }
}