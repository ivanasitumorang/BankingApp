package com.project.bankingapp.data.remote

import androidx.annotation.Keep

@Keep
data class PayeesRes(
    val status: String,
    val data: List<AccountRes>
)
