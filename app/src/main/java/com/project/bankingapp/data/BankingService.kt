package com.project.bankingapp.data

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST

interface BankingService {

    @POST("login")
    suspend fun login(): Response<*>

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