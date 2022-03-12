package com.project.bankingapp.data.remote

import androidx.annotation.Keep

@Keep
data class BalanceRes(
    val status: String,
    val accountNo: String,
    val balance: Double,
)
