package com.project.bankingapp.feature.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.bankingapp.base.ScreenState
import com.project.bankingapp.base.onError
import com.project.bankingapp.base.onSuccess
import com.project.bankingapp.feature.dashboard.dto.AccountSummary
import com.project.bankingapp.feature.dashboard.dto.TransactionHistory
import com.project.bankingapp.repository.BankingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.joda.time.format.DateTimeFormat
import javax.inject.Inject

@HiltViewModel
class DashboardVM @Inject constructor(
    private val repository: BankingRepository
) : ViewModel() {

    private val _accountSummary = MutableLiveData<ScreenState<AccountSummary>>()
    val accountSummary: LiveData<ScreenState<AccountSummary>> get() = _accountSummary
    fun getAccountSummary() {
        viewModelScope.launch(Dispatchers.IO) {
            _accountSummary.postValue(ScreenState.Loading)
            repository.getAccountSummary().onSuccess {
                _accountSummary.postValue(ScreenState.Success(it))
            }.onError { code, exception ->
                _accountSummary.postValue(ScreenState.Error(exception))
            }
        }
    }

    private val _trxHistoryList = MutableLiveData<ScreenState<List<TransactionHistory>>>()
    val trxHistoryList: LiveData<ScreenState<List<TransactionHistory>>> get() = _trxHistoryList
    fun getTransactionHistoryList() {
        viewModelScope.launch(Dispatchers.IO) {
            _trxHistoryList.postValue(ScreenState.Loading)
            repository.getTransactions().onSuccess {
                val trxHistoryList = mutableListOf<TransactionHistory>()

                it.groupBy { trx ->
                    DateTimeFormat.forPattern("dd MMM YYYY").print(trx.date)
                }.forEach { map ->
                    val dateTime = map.key
                    val list = map.value
                    trxHistoryList.add(
                        TransactionHistory(
                            dateString = dateTime,
                            transactions = list
                        )
                    )
                }

                _trxHistoryList.postValue(ScreenState.Success(trxHistoryList))
            }.onError { code, exception ->
                _trxHistoryList.postValue(ScreenState.Error(exception))
            }
        }
    }
}