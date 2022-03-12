package com.project.bankingapp.data

import com.project.bankingapp.data.remote.BalanceRes
import com.project.bankingapp.data.remote.LoginReq
import com.project.bankingapp.data.remote.LoginRes
import com.project.bankingapp.data.remote.PayeesRes
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface BankingService {

    @POST("login")
    suspend fun login(
        @Body loginReq: LoginReq
    ): Response<LoginRes>

    @POST("register")
    suspend fun register(): Response<*>

    @POST("transfer")
    suspend fun transfer(): Response<*>

    @GET("balance")
    suspend fun getBalance(
        @Header("Authorization") token: String
    ): Response<BalanceRes>

    @GET("payees")
    suspend fun getPayees(
        @Header("Authorization") token: String
    ): Response<PayeesRes>

    @GET("transactions")
    suspend fun getTransactions(): Response<*>
}