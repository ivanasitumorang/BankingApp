package com.project.bankingapp.data.remote

data class LoginRes(
    val status: String,
    val token: String,
    val username: String,
    val accountNo: String
)
