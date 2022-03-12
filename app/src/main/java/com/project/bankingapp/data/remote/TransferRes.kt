package com.project.bankingapp.data.remote

import androidx.annotation.Keep

@Keep
data class TransferRes(
    val status: String,
    val transactionId: String,
    val amount: Double,
    val description: String?,
    val recipientAccount: String
)
