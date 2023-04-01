package com.alramlawi.gasprovider.di

import android.content.Context
import androidx.room.Room
import com.alramlawi.gasprovider.data.local.datasource.customer.CustomerLocalDataSource
import com.alramlawi.gasprovider.data.local.datasource.customer.CustomerLocalDataSourceImpl
import com.alramlawi.gasprovider.data.local.datasource.receipt.ReceiptLocalDataSource
import com.alramlawi.gasprovider.data.local.datasource.receipt.ReceiptLocalDataSourceImpl
import com.alramlawi.gasprovider.data.local.room.database.GasDatabase
import com.alramlawi.gasprovider.data.remote.datasource.customer.CustomerFirebaseDataSource
import com.alramlawi.gasprovider.data.remote.datasource.customer.CustomerRemoteDataSource
import com.alramlawi.gasprovider.data.remote.datasource.receipts.ReceiptFirebaseDataSourceImpl
import com.alramlawi.gasprovider.data.remote.datasource.receipts.ReceiptRemoteDataSource
import com.alramlawi.gasprovider.data.repository.customer.CustomerRepository
import com.alramlawi.gasprovider.data.repository.customer.CustomerRepositoryImpl
import com.alramlawi.gasprovider.data.repository.receipt.ReceiptRepository
import com.alramlawi.gasprovider.data.repository.receipt.ReceiptRepositoryImpl
import com.alramlawi.gasprovider.utils.Validator
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DataModule {

    @Singleton
    @Provides
    fun provideGasDatabase(@ApplicationContext context: Context): GasDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            GasDatabase::class.java, "gas_provider_db"
        ).build()
    }

    @Singleton
    @Provides
    fun provideValidator(): Validator = Validator()


    @Singleton
    @Provides
    fun provideFirebaseFirestore(): FirebaseFirestore = Firebase.firestore

    @Singleton
    @Provides
    fun provideLocalCustomerDatasource(gasDatabase: GasDatabase): CustomerLocalDataSource =
        CustomerLocalDataSourceImpl(gasDatabase.customerDao)

    @Singleton
    @Provides
    fun provideRemoteCustomerDatasource(firebaseFirestore: FirebaseFirestore): CustomerRemoteDataSource =
        CustomerFirebaseDataSource(firebaseFirestore)

    @Singleton
    @Provides
    fun provideRemoteReceiptDatasource(firebaseFirestore: FirebaseFirestore): ReceiptRemoteDataSource =
        ReceiptFirebaseDataSourceImpl(firebaseFirestore)

    @Singleton
    @Provides
    fun provideLocaleReceiptDatasource(gasDatabase: GasDatabase): ReceiptLocalDataSource =
        ReceiptLocalDataSourceImpl(gasDatabase.receiptDao)

    @Singleton
    @Provides
    fun provideCustomerRepository(
        customerLocalDataSource: CustomerLocalDataSource,
        customerRemoteDataSource: CustomerRemoteDataSource
    ): CustomerRepository =
        CustomerRepositoryImpl(customerLocalDataSource, customerRemoteDataSource)

    @Singleton
    @Provides
    fun provideReceiptRepository(
        receiptLocalDataSource: ReceiptLocalDataSource,
        receiptRemoteDataSource: ReceiptRemoteDataSource,
        customerRepository: CustomerRepository,
    ): ReceiptRepository =
        ReceiptRepositoryImpl(receiptLocalDataSource, receiptRemoteDataSource, customerRepository)
}