package com.project.bankingapp.data.remote.api

import com.project.bankingapp.data.remote.*
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
    suspend fun register(
        @Body registerReq: RegisterReq
    ): Response<RegisterRes>

    @POST("transfer")
    suspend fun transfer(
        @Header("Authorization") token: String,
        @Body transferReq: TransferReq
    ): Response<TransferRes>

    @GET("balance")
    suspend fun getBalance(
        @Header("Authorization") token: String
    ): Response<BalanceRes>

    @GET("payees")
    suspend fun getPayees(
        @Header("Authorization") token: String
    ): Response<PayeesRes>

    @GET("transactions")
    suspend fun getTransactions(
        @Header("Authorization") token: String
    ): Response<TransactionsRes>
}