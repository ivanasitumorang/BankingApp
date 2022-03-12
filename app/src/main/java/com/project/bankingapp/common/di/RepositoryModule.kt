package com.project.bankingapp.common.di

import com.project.bankingapp.data.BankingService
import com.project.bankingapp.repository.BankingRepository
import com.project.bankingapp.repository.BankingRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    fun provideBankingRepository(service: BankingService): BankingRepository {
        return BankingRepositoryImpl(service)
    }
}