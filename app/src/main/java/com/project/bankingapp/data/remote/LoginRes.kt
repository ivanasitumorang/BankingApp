package com.project.bankingapp.data.remote

import com.project.bankingapp.base.BaseResponse

data class LoginRes(
    override val status: String,
    val token: String,
    val username: String,
    val accountNo: String
): BaseResponse
