package com.project.bankingapp.data.remote

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class TransactionsRes(
    val status: String,
    val data: List<TransactionRes>
) {

    data class TransactionRes(
        val transactionId: String,
        val amount: Double,
        val transactionDate: String,
        val description: String,
        val transactionType: String,
        @SerializedName("sender", alternate = ["receipient"]) val sender: AccountRes
    )
}
