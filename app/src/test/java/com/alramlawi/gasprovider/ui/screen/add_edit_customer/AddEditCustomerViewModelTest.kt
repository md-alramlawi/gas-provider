package com.alramlawi.gasprovider.ui.screen.add_edit_customer

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import com.alramlawi.gasprovider.MainCoroutineRule
import com.alramlawi.gasprovider.data.local.room.entity.CustomerEntity
import com.alramlawi.gasprovider.data.repository.customer.CustomerRepository
import com.alramlawi.gasprovider.data.repository.customer.FakeCustomerRepository
import com.alramlawi.gasprovider.utils.Validator
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.not
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class AddEditCustomerViewModelTest {

    private lateinit var addEditCustomerViewModel: AddEditCustomerViewModel

    private lateinit var customerRepository: CustomerRepository

    private lateinit var savedStateHandle: SavedStateHandle

    private lateinit var validator: Validator

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setupRepositoryAndStateHandle() {
        customerRepository = FakeCustomerRepository()
        val c1 = CustomerEntity("Firstname1", "Lastname1", id = "id_1")
        val c2 = CustomerEntity("Firstname2", "Lastname2")
        val c3 = CustomerEntity("Firstname3", "Lastname3")
        runBlocking {
            customerRepository.addCustomer(c1)
            customerRepository.addCustomer(c2)
            customerRepository.addCustomer(c3)
        }
        savedStateHandle = SavedStateHandle()
        validator = Validator()
    }


    @Test
    fun flowCustomers_notEmpty() = runBlocking{
        assertThat(customerRepository.customersStream.first(), not(emptyList()))
    }


    @Test
    fun createCustomer_retrievesCustomer()= runBlocking{

        addEditCustomerViewModel = AddEditCustomerViewModel(customerRepository, savedStateHandle, validator)
        addEditCustomerViewModel.onFirstNameChanged("Majd")
        addEditCustomerViewModel.onLastNameChanged("Alramlawi")

        addEditCustomerViewModel.saveCustomer()

        assertThat(addEditCustomerViewModel.state.first().saveComplete, `is` (true))
        assertThat(customerRepository.customersStream.first().map { it.firstName }.contains("Majd"), `is`(true))
    }

    @Test
    fun updateCustomer_retrievesCustomer()= runTest{
        savedStateHandle["item_id"] = "id_1"
        val originalList = customerRepository.customersStream.last()
        addEditCustomerViewModel = AddEditCustomerViewModel(customerRepository, savedStateHandle, validator)
        addEditCustomerViewModel.onFirstNameChanged("new First name")

        assertThat(addEditCustomerViewModel.state.value.saveComplete, `is` (false))

        addEditCustomerViewModel.saveCustomer()

        val currentList = customerRepository.customersStream.last()

        assertThat(addEditCustomerViewModel.state.value.saveComplete, `is` (true))
        assertThat(currentList.map { it.firstName }.contains("new First name"), `is` (true))
        assertThat(currentList.size, `is`(originalList.size))
    }
}