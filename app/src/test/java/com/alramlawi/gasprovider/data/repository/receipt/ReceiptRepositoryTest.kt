package com.alramlawi.gasprovider.data.repository.receipt

import com.alramlawi.gasprovider.MainCoroutineRule
import com.alramlawi.gasprovider.data.local.datasource.customer.CustomerLocalDataSource
import com.alramlawi.gasprovider.data.local.datasource.customer.FakeCustomerLocalDataSource
import com.alramlawi.gasprovider.data.local.datasource.receipt.FakeReceiptLocalDataSource
import com.alramlawi.gasprovider.data.local.datasource.receipt.ReceiptLocalDataSource
import com.alramlawi.gasprovider.data.local.room.entity.CustomerEntity
import com.alramlawi.gasprovider.data.local.room.entity.ReceiptEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.nullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.*
import kotlin.collections.LinkedHashMap

@ExperimentalCoroutinesApi
class ReceiptRepositoryTest {

    private val r1 = ReceiptEntity(
        "id_1",
        "l",
        50.0,
        20.0,
        2.0,
        Date(System.currentTimeMillis() - (24 * 60 * 60 * 1000))
    )

    private val r2 = ReceiptEntity(
        "id_2",
        "Majd Alramlawi",
        50.0,
        20.0,
        2.0,
        Date()
    )

    private val c1 = CustomerEntity("Leen", "Alramlawi", id = "id_1")
    private val c2 = CustomerEntity("Majd", "Alramlawi", id = "id_2")

    private val receiptsData: LinkedHashMap<String, ReceiptEntity> = linkedMapOf(r1.id to r1, r2.id to r2)
    private val customersData: LinkedHashMap<String, CustomerEntity> = linkedMapOf(c1.id to c1, c2.id to c2)
    private lateinit var receiptRepository: ReceiptRepository
    private lateinit var receiptLocalDataSource: ReceiptLocalDataSource
    private lateinit var customerLocalDataSource: CustomerLocalDataSource


    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()


    @Before
    fun setUp() {
        receiptLocalDataSource = FakeReceiptLocalDataSource(receiptsData)
        customerLocalDataSource = FakeCustomerLocalDataSource(customersData)
        receiptRepository = ReceiptRepositoryImpl(receiptLocalDataSource, customerLocalDataSource, Dispatchers.Main)
    }

    @Test
    fun receiptStream_returnsCorrectCustomerName() = mainCoroutineRule.runBlockingTest {
        val receipts = receiptRepository.receiptStream.first()

        assertThat(receipts.find { it.customerName == "l" }, `is`(nullValue()))
        assertThat(receipts.find { it.customerId == "id_1" }?.customerName, `is`("Leen Alramlawi"))
    }

}