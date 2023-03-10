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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.alramlawi.gasprovider.R
import com.alramlawi.gasprovider.data.local.room.entity.CustomerEntity
import com.alramlawi.gasprovider.ui.composable.SimpleTextField

@Composable
fun AddEditCustomerScreen(
    navController: NavController,
    viewModel: AddEditCustomerViewModel = hiltViewModel()
) {

    val context = LocalContext.current
    val state by viewModel.state.collectAsState()

    AddEditCustomerContent(
        isNewCustomer = state.isNewCustomer,
        customerEntity = state.customer,
        firstNameError = state.firstNameError,
        lastNameError = state.lastNameError,
        onFirstNameChanged = viewModel::onFirstNameChanged,
        onLastNameChanged = viewModel::onLastNameChanged,
        onClickSave = viewModel::saveCustomer
    )

    LaunchedEffect(key1 = state.saveComplete){
        if(state.saveComplete){
            Toast.makeText(context, context.getText(R.string.item_saved), Toast.LENGTH_SHORT).show()

            if(!state.isNewCustomer){
                navController.navigateUp()
            }
        }
    }
}


@Composable
fun AddEditCustomerContent(
    isNewCustomer: Boolean,
    customerEntity: CustomerEntity,
    firstNameError: Boolean,
    lastNameError: Boolean,
    onFirstNameChanged: (String) -> Unit,
    onLastNameChanged: (String) -> Unit,
    onClickSave: () -> Unit,
) {

    val title = if(isNewCustomer)
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
            style = MaterialTheme.typography.h6,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.weight(0.2f))

        SimpleTextField(
            modifier = Modifier.fillMaxWidth(),
            isError = firstNameError,
            label = stringResource(id = R.string.first_name_title),
            value = customerEntity.firstName,
            onValueChanged = onFirstNameChanged
        )


        SimpleTextField(
            modifier = Modifier.fillMaxWidth().padding(top = 12.dp),
            isError = lastNameError,
            label = stringResource(id = R.string.last_name_title),
            value = customerEntity.lastName,
            onValueChanged = onLastNameChanged
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
    AddEditCustomerContent(
        isNewCustomer = true,
        customerEntity = CustomerEntity("", ""),
        firstNameError = false,
        lastNameError = true,
        onFirstNameChanged = {},
        onLastNameChanged = {}
    ) {

    }
}