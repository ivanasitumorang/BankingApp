package com.project.bankingapp.repository

import com.project.bankingapp.base.Result
import com.project.bankingapp.data.remote.LoginRes

interface BankingRepository {
    suspend fun login(username: String, password: String): Result<LoginRes>
}