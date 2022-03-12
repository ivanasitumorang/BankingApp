package com.project.bankingapp.data.remote

import androidx.annotation.Keep

@Keep
data class LoginReq(
    val username: String,
    val password: String
)
