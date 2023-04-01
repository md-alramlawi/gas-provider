package com.alramlawi.gasprovider.data.mapper

import com.alramlawi.gasprovider.data.local.room.entity.ReceiptEntity
import com.google.firebase.firestore.DocumentSnapshot
import java.util.*

val ReceiptEntity.toHashMap: HashMap<String, Any>
    get() = hashMapOf(
        "customer_id" to customerId,
        "amount" to amount,
        "cash_payment" to cashPayment,
        "remaining_payment" to remainingPayment,
        "date" to date.time,
    )

val DocumentSnapshot.toReceipt: ReceiptEntity
    get() = ReceiptEntity(
        id = this.id,
        customerId = data?.get("customer_id") as String,
        amount = data?.get("amount") as Double,
        cashPayment = data?.get("cash_payment") as Double,
        remainingPayment = data?.get("remaining_payment") as Double,
        date = (data?.get("date") as Long).run { Date(this) }
    )