package com.project.bankingapp.data.remote

import androidx.annotation.Keep

@Keep
data class RegisterRes(
    val status: String,
    val token: String
)
