package com.project.bankingapp.repository

import com.auth0.android.jwt.JWT
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.project.bankingapp.base.Result
import com.project.bankingapp.base.suspendTryCatch
import com.project.bankingapp.data.BankingService
import com.project.bankingapp.data.local.AuthenticationPref
import com.project.bankingapp.data.remote.*
import com.project.bankingapp.feature.dashboard.AccountSummary

class BankingRepositoryImpl(
    private val authenticationPref: AuthenticationPref,
    private val service: BankingService
) : BankingRepository {
    override suspend fun login(username: String, password: String): Result<LoginRes> =
        suspendTryCatch {
            val response = service.login(LoginReq(username, password))

            if (response.isSuccessful) {
                val loginRes = response.body() as LoginRes
                authenticationPref.setToken(loginRes.token)
                Result.Success(loginRes)
            } else {
                val type = object : TypeToken<ErrorRes>() {}.type
                val errorRes =
                    Gson().fromJson<ErrorRes>(response.errorBody()?.string(), type)

                Result.Error(
                    code = response.code(),
                    exception = Exception(errorRes.error)
                )
            }
        }

    override suspend fun getAccountSummary(): Result<AccountSummary> = suspendTryCatch {
        val accountBalance = getAccountBalance()
        val name = JWT(authenticationPref.getToken()).getClaim("username").asString() ?: "-"
        val accountSummary = AccountSummary(
            name = name,
            balance = accountBalance.balance,
            accountNo = accountBalance.accountNo
        )
        Result.Success(accountSummary)
    }

    override suspend fun getPayees(): Result<PayeesRes> = suspendTryCatch {
        val response = service.getPayees(authenticationPref.getToken())
        if (response.isSuccessful) {

            Result.Success(response.body() as PayeesRes)
        } else {
            val type = object : TypeToken<ErrorRes>() {}.type
            val errorRes =
                Gson().fromJson<ErrorRes>(response.errorBody()?.string(), type)
            Result.Success(
                PayeesRes(
                    status = errorRes.status,
                    data = emptyList()
                )
            )
        }
    }

    private suspend fun getAccountBalance(): BalanceRes {
        val response = service.getBalance(authenticationPref.getToken())
        service.getBalance(authenticationPref.getToken()).apply {
            return if (isSuccessful) {
                response.body() as BalanceRes
            } else {
                val type = object : TypeToken<ErrorRes>() {}.type
                val errorRes =
                    Gson().fromJson<ErrorRes>(response.errorBody()?.string(), type)
                BalanceRes(
                    status = errorRes.status,
                    accountNo = "-",
                    balance = 0.0
                )
            }
        }
    }


}