package com.project.bankingapp.data.remote

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class AccountRes(
    @SerializedName("accountNo") val no: String,
    @SerializedName("accountHolder") val name: String
)
