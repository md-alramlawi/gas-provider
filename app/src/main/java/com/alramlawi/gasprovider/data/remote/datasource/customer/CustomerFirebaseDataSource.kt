package com.alramlawi.gasprovider.data.remote.datasource.customer

import com.alramlawi.gasprovider.data.Result
import com.alramlawi.gasprovider.data.local.room.entity.CustomerEntity
import com.alramlawi.gasprovider.data.mapper.toCustomer
import com.alramlawi.gasprovider.data.mapper.toHashMap
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import java.util.*

class CustomerFirebaseDataSource(
    private val firebaseStore: FirebaseFirestore
) : CustomerRemoteDataSource {
    companion object {
        private const val CUSTOMERS_TABLE = "customers"
    }

    override suspend fun getCustomers(): Result<List<CustomerEntity>> {
        return try {
            firebaseStore.collection(CUSTOMERS_TABLE).get().await().documents.map {
                it.toCustomer
            }.let { Result.Success(it) }

        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun saveCustomer(customerEntity: CustomerEntity): Result<Unit> {
        return try {
            firebaseStore
                .collection(CUSTOMERS_TABLE)
                .document(customerEntity.id)
                .set(customerEntity.toHashMap)
                .await()
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun updateCustomer(customerEntity: CustomerEntity): Result<Unit> {
        return try {
            firebaseStore
                .collection(CUSTOMERS_TABLE)
                .document(customerEntity.id)
                .update(customerEntity.toHashMap)
                .await()
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun updateActivityLastDate(id: String): Result<Unit> {
        return try {
            firebaseStore
                .collection(CUSTOMERS_TABLE)
                .document(id)
                .update("last_update", Date().time)
                .await()
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun deleteCustomer(id: String): Result<Unit> {
        return try {
            firebaseStore
                .collection(CUSTOMERS_TABLE)
                .document(id)
                .delete()
                .await()
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

}