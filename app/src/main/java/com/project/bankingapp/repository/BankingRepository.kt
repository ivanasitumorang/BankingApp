package com.project.bankingapp.repository

import com.project.bankingapp.base.Result
import com.project.bankingapp.common.dto.Account
import com.project.bankingapp.data.remote.LoginRes
import com.project.bankingapp.data.remote.RegisterRes
import com.project.bankingapp.data.remote.TransferRes
import com.project.bankingapp.feature.dashboard.dto.AccountSummary
import com.project.bankingapp.feature.dashboard.dto.Transaction

interface BankingRepository {
    suspend fun login(username: String, password: String): Result<LoginRes>
    suspend fun register(username: String, password: String): Result<RegisterRes>
    suspend fun logout(): Result<Unit>
    suspend fun getAccountSummary(): Result<AccountSummary>
    suspend fun getPayees(): Result<List<Account>>
    suspend fun getTransactions(): Result<List<Transaction>>
    suspend fun transfer(
        accountNo: String,
        amount: Double,
        description: String?
    ): Result<TransferRes>
}