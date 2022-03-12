package com.project.bankingapp.data.remote

import androidx.annotation.Keep

@Keep
data class RegisterReq(
    val username: String,
    val password: String
)
