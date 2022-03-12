package com.project.bankingapp.common.di

import com.project.bankingapp.data.remote.api.BankingService
import com.project.bankingapp.data.local.AuthenticationPref
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
    fun provideBankingRepository(
        authenticationPref: AuthenticationPref,
        service: BankingService
    ): BankingRepository {
        return BankingRepositoryImpl(authenticationPref, service)
    }
}