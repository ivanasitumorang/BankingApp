package com.project.bankingapp.common.di

import com.project.bankingapp.BuildConfig
import com.project.bankingapp.data.BankingService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .build()
    }

    @Provides
    fun provideBankingNetwork(retrofit: Retrofit): BankingService {
        return retrofit.create(BankingService::class.java)
    }
}