package com.alramlawi.gasprovider.di

import android.content.Context
import androidx.room.Room
import com.alramlawi.gasprovider.data.local.datasource.customer.CustomerLocalDataSource
import com.alramlawi.gasprovider.data.local.datasource.customer.CustomerLocalDataSourceImpl
import com.alramlawi.gasprovider.data.local.datasource.receipt.ReceiptLocalDataSource
import com.alramlawi.gasprovider.data.local.datasource.receipt.ReceiptLocalDataSourceImpl
import com.alramlawi.gasprovider.data.local.room.database.GasDatabase
import com.alramlawi.gasprovider.data.repository.customer.CustomerRepository
import com.alramlawi.gasprovider.data.repository.customer.CustomerRepositoryImpl
import com.alramlawi.gasprovider.data.repository.receipt.ReceiptRepository
import com.alramlawi.gasprovider.data.repository.receipt.ReceiptRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object DataModule {

    @Provides
    fun provideGasDatabase(@ApplicationContext context: Context): GasDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            GasDatabase::class.java, "gas_provider_db"
        ).build()
    }

    @Provides
    fun provideCustomerDatasource(gasDatabase: GasDatabase): CustomerLocalDataSource =
        CustomerLocalDataSourceImpl(gasDatabase.customerDao)

    @Provides
    fun provideReceiptDatasource(gasDatabase: GasDatabase): ReceiptLocalDataSource =
        ReceiptLocalDataSourceImpl(gasDatabase.receiptDao)

    @Provides
    fun provideCustomerRepository(customerLocalDataSource: CustomerLocalDataSource): CustomerRepository =
        CustomerRepositoryImpl(customerLocalDataSource)

    @Provides
    fun provideReceiptRepository(receiptLocalDataSource: ReceiptLocalDataSource, customerLocalDataSource: CustomerLocalDataSource): ReceiptRepository =
        ReceiptRepositoryImpl(receiptLocalDataSource, customerLocalDataSource)
}