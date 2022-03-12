package com.project.bankingapp.data.remote

import androidx.annotation.Keep

@Keep
data class PayeesRes(
    val status: String,
    val data: List<PayeeRes>
) {
    data class PayeeRes(
        val id: String,
        val name: String,
        val accountNo: String,
    )
}
