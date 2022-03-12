package com.project.bankingapp.data.remote

import androidx.annotation.Keep

@Keep
data class AccountRes(
    val id: String,
    val name: String,
    val accountNo: String,
)
