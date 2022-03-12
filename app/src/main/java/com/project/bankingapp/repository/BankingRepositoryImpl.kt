package com.project.bankingapp.repository

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.project.bankingapp.base.Result
import com.project.bankingapp.base.suspendTryCatch
import com.project.bankingapp.data.BankingService
import com.project.bankingapp.data.remote.ErrorRes
import com.project.bankingapp.data.remote.LoginReq
import com.project.bankingapp.data.remote.LoginRes

class BankingRepositoryImpl(
    private val service: BankingService
) : BankingRepository {
    override suspend fun login(username: String, password: String): Result<LoginRes> =
        suspendTryCatch {
            val response = service.login(LoginReq(username, password))

            if (response.isSuccessful) {
                val loginRes = response.body() as LoginRes

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
}