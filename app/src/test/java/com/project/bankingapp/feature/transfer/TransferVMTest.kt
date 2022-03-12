package com.project.bankingapp.feature.transfer

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.project.bankingapp.base.Result
import com.project.bankingapp.base.ScreenState
import com.project.bankingapp.common.dto.Account
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
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class TransferVMTest {
    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var repository: BankingRepository

    private lateinit var viewModel: TransferVM

    @Mock
    private lateinit var observerTransferResult: Observer<ScreenState<Unit>>

    @Mock
    private lateinit var observerPayeeList: Observer<ScreenState<List<Account>>>

    @Mock
    private lateinit var observerSelectedPayee: Observer<Account>

    @Before
    fun setUp() {
        viewModel = TransferVM(repository)
    }

    @Test
    fun `do transfer successfully`() {
        testCoroutineRule.runBlockingTest {
            doReturn(Result.Success(DummyData.transferRes))
                .`when`(repository)
                .transfer(anyString(), anyDouble(), anyString())

            viewModel.transfer("test", "20.0", "test")
            viewModel.transferResult.observeForever(observerTransferResult)

            runBlockingTest {
                verify(repository).transfer(anyString(), anyDouble(), anyString())
            }

            verify(observerTransferResult).onChanged(ScreenState.Loading)
            verify(observerTransferResult).onChanged(ScreenState.Success(Unit))
            viewModel.transferResult.removeObserver(observerTransferResult)
        }
    }

    @Test
    fun `do transfer failed`() {
        testCoroutineRule.runBlockingTest {
            doReturn(Result.Error(0, DummyData.exception))
                .`when`(repository)
                .transfer(anyString(), anyDouble(), anyString())

            viewModel.transfer("test", "20.0", "test")
            viewModel.transferResult.observeForever(observerTransferResult)

            runBlockingTest {
                verify(repository).transfer(anyString(), anyDouble(), anyString())
            }

            verify(observerTransferResult).onChanged(ScreenState.Loading)
            verify(observerTransferResult).onChanged(ScreenState.Error(DummyData.exception))
            viewModel.transferResult.removeObserver(observerTransferResult)
        }
    }

    @Test
    fun `get payee list successfully`() {
        testCoroutineRule.runBlockingTest {
            val payeeList = DummyData.payeeList

            doReturn(Result.Success(payeeList))
                .`when`(repository)
                .getPayees()

            viewModel.getPayeeList()
            viewModel.payeeList.observeForever(observerPayeeList)

            runBlockingTest {
                verify(repository).getPayees()
            }

            verify(observerPayeeList).onChanged(ScreenState.Loading)
            verify(observerPayeeList).onChanged(ScreenState.Success(payeeList))
            viewModel.payeeList.removeObserver(observerPayeeList)
        }
    }

    @Test
    fun `get payee list failed`() {
        testCoroutineRule.runBlockingTest {
            doReturn(Result.Error(0, DummyData.exception))
                .`when`(repository)
                .getPayees()

            viewModel.getPayeeList()
            viewModel.payeeList.observeForever(observerPayeeList)

            runBlockingTest {
                verify(repository).getPayees()
            }

            verify(observerPayeeList).onChanged(ScreenState.Loading)
            verify(observerPayeeList).onChanged(ScreenState.Error(DummyData.exception))
            viewModel.payeeList.removeObserver(observerPayeeList)
        }
    }

    @Test
    fun `set selected payee`() {
        testCoroutineRule.runBlockingTest {

            viewModel.selectPayee(DummyData.payeeList[0])
            viewModel.selectedPayee.observeForever(observerSelectedPayee)

            verify(observerSelectedPayee).onChanged(DummyData.payeeList[0])
            viewModel.selectedPayee.removeObserver(observerSelectedPayee)
        }
    }
}