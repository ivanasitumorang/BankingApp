package com.project.bankingapp.data.remote

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class TransferReq(
    val amount: Double,
    val description: String?,
    @SerializedName("receipientAccountNo") val recipientAccountNo: String
)
