package com.project.bankingapp.data.remote

import com.project.bankingapp.base.BaseResponse

data class ErrorRes(
    override val status: String,
    val error: String
) : BaseResponse