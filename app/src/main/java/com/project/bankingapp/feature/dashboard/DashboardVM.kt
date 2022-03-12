package com.project.bankingapp.feature.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.bankingapp.base.ScreenState
import com.project.bankingapp.base.onError
import com.project.bankingapp.base.onSuccess
import com.project.bankingapp.repository.BankingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardVM @Inject constructor(
    private val repository: BankingRepository
) : ViewModel() {

    init {
        getAccountSummary()
    }

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
}