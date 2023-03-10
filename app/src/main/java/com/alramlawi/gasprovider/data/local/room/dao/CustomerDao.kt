package com.alramlawi.gasprovider.data.local.room.dao

import androidx.room.*
import com.alramlawi.gasprovider.data.local.room.entity.CustomerEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface CustomerDao {

    @Query("SELECT * FROM customer_entity")
    fun flowCustomers(): Flow<List<CustomerEntity>>

    @Query("SELECT * FROM customer_entity")
    suspend fun getCustomers(): List<CustomerEntity>

    @Query("SELECT * FROM customer_entity WHERE id = :id")
    suspend fun getCustomerById(id: String): CustomerEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCustomer(customerEntity: CustomerEntity)

    @Update
    suspend fun updateCustomer(customerEntity: CustomerEntity)

    @Query("DELETE FROM customer_entity WHERE id = :id")
    suspend fun deleteCustomerById(id: String)
}
