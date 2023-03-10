package com.alramlawi.gasprovider.ui.screen.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.alramlawi.gasprovider.MainCoroutineRule
import com.alramlawi.gasprovider.data.local.room.entity.ReceiptEntity
import com.alramlawi.gasprovider.data.repository.receipt.FakeReceiptRepository
import com.alramlawi.gasprovider.data.repository.receipt.ReceiptRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.*

@ExperimentalCoroutinesApi
class HomeViewModelTest {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var receiptRepository: ReceiptRepository

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setupRepository() {
        receiptRepository = FakeReceiptRepository()
        val yesterdayReceipt = ReceiptEntity(
            "id_1",
            "Majd",
            50.0,
            20.0,
            2.0,
            Date(System.currentTimeMillis() - (24 * 60 * 60 * 1000))
        )

        val todayReceipt = ReceiptEntity(
            "id_2",
            "Leen",
            50.0,
            20.0,
            2.0,
            Date()
        )
        runBlocking {
            receiptRepository.addReceipt(yesterdayReceipt)
            receiptRepository.addReceipt(todayReceipt)
        }
        homeViewModel = HomeViewModel(receiptRepository)
    }

    @Test
    fun receiptsFlow_returnsTodayItems() = runBlocking {
        assertThat(homeViewModel.state.first().receipts.size, `is`(1))
    }
}

