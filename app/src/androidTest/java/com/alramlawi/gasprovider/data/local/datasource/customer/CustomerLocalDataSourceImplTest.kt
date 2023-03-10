package com.alramlawi.gasprovider.data.local.datasource.customer

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.alramlawi.gasprovider.data.local.room.database.GasDatabase
import com.alramlawi.gasprovider.data.local.room.entity.CustomerEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@MediumTest
class CustomerLocalDataSourceImplTest {

    private lateinit var customerLocalDataSource: CustomerLocalDataSource
    private lateinit var database: GasDatabase

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            GasDatabase::class.java
        )
            .allowMainThreadQueries()
            .build()

        customerLocalDataSource =
            CustomerLocalDataSourceImpl(
                database.customerDao,
                Dispatchers.Main
            )
    }

    @After
    fun cleanUp() {
        database.close()
    }


    @Test
    fun saveCustomer_retrievesCustomer() = runBlocking {
        val newCustomer = CustomerEntity("Mohammed", "Alramlawi")
        customerLocalDataSource.saveCustomer(newCustomer)

        val result = customerLocalDataSource.getCustomer(newCustomer.id)
        assertTrue(result != null)
        result as CustomerEntity
        assertEquals(result.id, newCustomer.id)
        assertEquals(result.firstName, "Mohammed")
        assertEquals(result.lastName, "Alramlawi")
    }

    @Test
    fun updateCustomer_retrievedCustomerIsUpdated() = runBlocking {
        val newCustomer = CustomerEntity("Mohammed", "Alramlawi")
        customerLocalDataSource.saveCustomer(newCustomer)

        customerLocalDataSource.updateCustomer(
            newCustomer.copy(
                firstName = "Abu Rabi3",
                lastName = "Ramlawi"
            )
        )
        val result = customerLocalDataSource.getCustomer(newCustomer.id)
        assertTrue(result != null)
        result as CustomerEntity
        assertEquals(result.id, newCustomer.id)
        assertEquals(result.firstName, "Abu Rabi3")
        assertEquals(result.lastName, "Ramlawi")
    }
}