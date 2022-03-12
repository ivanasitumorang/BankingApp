package com.project.bankingapp.feature.dashboard

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.project.bankingapp.base.Result
import com.project.bankingapp.base.ScreenState
import com.project.bankingapp.data.remote.ErrorRes
import com.project.bankingapp.feature.dashboard.dto.AccountSummary
import com.project.bankingapp.feature.dashboard.dto.TransactionHistory
import com.project.bankingapp.repository.BankingRepository
import com.project.bankingapp.util.DummyData
import com.project.bankingapp.util.TestCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class DashboardVMTest {
    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var repository: BankingRepository

    private lateinit var viewModel: DashboardVM

    @Mock
    private lateinit var observerAccountSummary: Observer<ScreenState<AccountSummary>>

    @Mock
    private lateinit var observerTrxHistoryList: Observer<ScreenState<List<TransactionHistory>>>


    @Before
    fun setUp() {
        viewModel = DashboardVM(repository)
    }

    @Test
    fun `get account summary successfully`() {
        testCoroutineRule.runBlockingTest {
            val accountSummary = AccountSummary(
                name = "test",
                balance = 1.0,
                accountNo = "test"
            )
            Mockito.doReturn(Result.Success(accountSummary))
                .`when`(repository)
                .getAccountSummary()

            viewModel.getAccountSummary()
            viewModel.accountSummary.observeForever(observerAccountSummary)

            runBlockingTest {
                Mockito.verify(repository).getAccountSummary()
            }

            Mockito.verify(observerAccountSummary).onChanged(ScreenState.Loading)
            Mockito.verify(observerAccountSummary).onChanged(ScreenState.Success(accountSummary))
            viewModel.accountSummary.removeObserver(observerAccountSummary)
        }
    }

    @Test
    fun `get account summary failed`() {
        testCoroutineRule.runBlockingTest {
            val errorRes = ErrorRes(
                status = "test",
                error = "test"
            )
            val exception = Exception(errorRes.error)
            Mockito.doReturn(Result.Error(0, exception))
                .`when`(repository)
                .getAccountSummary()

            viewModel.getAccountSummary()
            viewModel.accountSummary.observeForever(observerAccountSummary)

            runBlockingTest {
                Mockito.verify(repository).getAccountSummary()
            }

            Mockito.verify(observerAccountSummary).onChanged(ScreenState.Loading)
            Mockito.verify(observerAccountSummary).onChanged(ScreenState.Error(exception))
            viewModel.accountSummary.removeObserver(observerAccountSummary)
        }
    }

    @Test
    fun `get transaction history list successfully`() {
        testCoroutineRule.runBlockingTest {
            val trxList = DummyData.transactions1
            val trxHistoryList = DummyData.trxHistoryList
            Mockito.doReturn(Result.Success(trxList))
                .`when`(repository)
                .getTransactions()

            viewModel.getTransactionHistoryList()
            viewModel.trxHistoryList.observeForever(observerTrxHistoryList)

            runBlockingTest {
                Mockito.verify(repository).getTransactions()
            }

            Mockito.verify(observerTrxHistoryList).onChanged(ScreenState.Loading)
            Mockito.verify(observerTrxHistoryList).onChanged(ScreenState.Success(trxHistoryList))
            viewModel.trxHistoryList.removeObserver(observerTrxHistoryList)
        }
    }

    @Test
    fun `get transaction history list failed`() {
        testCoroutineRule.runBlockingTest {
            val errorRes = ErrorRes(
                status = "test",
                error = "test"
            )
            val exception = Exception(errorRes.error)
            Mockito.doReturn(Result.Error(0, exception))
                .`when`(repository)
                .getTransactions()

            viewModel.getTransactionHistoryList()
            viewModel.trxHistoryList.observeForever(observerTrxHistoryList)

            runBlockingTest {
                Mockito.verify(repository).getTransactions()
            }

            Mockito.verify(observerTrxHistoryList).onChanged(ScreenState.Loading)
            Mockito.verify(observerTrxHistoryList).onChanged(ScreenState.Error(exception))
            viewModel.trxHistoryList.removeObserver(observerTrxHistoryList)
        }
    }
}