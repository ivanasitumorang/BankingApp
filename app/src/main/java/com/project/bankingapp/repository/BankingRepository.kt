package com.project.bankingapp.repository

import com.project.bankingapp.base.Result
import com.project.bankingapp.data.remote.LoginRes
import com.project.bankingapp.data.remote.PayeesRes
import com.project.bankingapp.feature.dashboard.dto.AccountSummary
import com.project.bankingapp.feature.dashboard.Transaction

interface BankingRepository {
    suspend fun login(username: String, password: String): Result<LoginRes>
    suspend fun getAccountSummary(): Result<AccountSummary>
    suspend fun getPayees(): Result<PayeesRes>
    suspend fun getTransactions(): Result<List<Transaction>>
}