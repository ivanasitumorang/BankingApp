package com.project.bankingapp.data

import com.project.bankingapp.base.BaseResponse
import com.project.bankingapp.data.remote.LoginReq
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface BankingService {

    @POST("login")
    suspend fun login(
        @Body loginReq: LoginReq
    ): Response<BaseResponse>

    @POST("register")
    suspend fun register(): Response<*>

    @POST("transfer")
    suspend fun transfer(): Response<*>

    @GET("balance")
    suspend fun getBalance(): Response<*>

    @GET("payees")
    suspend fun getPayees(): Response<*>

    @GET("transactions")
    suspend fun getTransactions(): Response<*>
}