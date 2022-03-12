package com.project.bankingapp.feature.authentication

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.project.bankingapp.base.Result
import com.project.bankingapp.base.ScreenState
import com.project.bankingapp.data.remote.ErrorRes
import com.project.bankingapp.data.remote.LoginRes
import com.project.bankingapp.data.remote.RegisterRes
import com.project.bankingapp.repository.BankingRepository
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
class AuthenticationVMTest {
    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var repository: BankingRepository

    @Mock
    private lateinit var observerLoginResult: Observer<ScreenState<Unit>>

    @Mock
    private lateinit var observerRegisterResult: Observer<ScreenState<Unit>>

    private lateinit var viewModel: AuthenticationVM

    @Before
    fun setUp() {
        viewModel = AuthenticationVM(repository)
    }

    @Test
    fun `login successfully`() {
        testCoroutineRule.runBlockingTest {
            val loginRes = LoginRes(
                status = "test",
                token = "test",
                username = "test",
                accountNo = "test"
            )
            doReturn(Result.Success(loginRes))
                .`when`(repository)
                .login(anyString(), anyString())

            viewModel.login("", "")
            viewModel.loginResult.observeForever(observerLoginResult)

            runBlockingTest {
                verify(repository).login(anyString(), anyString())
            }

            verify(observerLoginResult).onChanged(ScreenState.Loading)
            verify(observerLoginResult).onChanged(ScreenState.Success(Unit))
            viewModel.loginResult.removeObserver(observerLoginResult)
        }
    }

    @Test
    fun `login failed`() {
        testCoroutineRule.runBlockingTest {
            val errorRes = ErrorRes(
                status = "test",
                error = "test"
            )
            val exception = Exception(errorRes.error)
            doReturn(Result.Error(0, exception))
                .`when`(repository)
                .login(anyString(), anyString())

            viewModel.login("", "")
            viewModel.loginResult.observeForever(observerLoginResult)

            runBlockingTest {
                verify(repository).login(anyString(), anyString())
            }

            verify(observerLoginResult).onChanged(ScreenState.Loading)
            verify(observerLoginResult).onChanged(ScreenState.Error(exception))
            viewModel.loginResult.removeObserver(observerLoginResult)
        }
    }

    @Test
    fun `register successfully`() {
        testCoroutineRule.runBlockingTest {
            val registerRes = RegisterRes(
                status = "test",
                token = "test"
            )
            doReturn(Result.Success(registerRes))
                .`when`(repository)
                .register(anyString(), anyString())

            viewModel.register("", "")
            viewModel.registerResult.observeForever(observerRegisterResult)

            runBlockingTest {
                verify(repository).register(anyString(), anyString())
            }

            verify(observerRegisterResult).onChanged(ScreenState.Loading)
            verify(observerRegisterResult).onChanged(ScreenState.Success(Unit))
            viewModel.registerResult.removeObserver(observerRegisterResult)
        }
    }

    @Test
    fun `register failed`() {
        testCoroutineRule.runBlockingTest {
            val errorRes = ErrorRes(
                status = "test",
                error = "test"
            )
            val exception = Exception(errorRes.error)
            doReturn(Result.Error(0, exception))
                .`when`(repository)
                .register(anyString(), anyString())

            viewModel.register("", "")
            viewModel.registerResult.observeForever(observerRegisterResult)

            runBlockingTest {
                verify(repository).register(anyString(), anyString())
            }

            verify(observerRegisterResult).onChanged(ScreenState.Loading)
            verify(observerRegisterResult).onChanged(ScreenState.Error(exception))
            viewModel.registerResult.removeObserver(observerRegisterResult)
        }
    }
}