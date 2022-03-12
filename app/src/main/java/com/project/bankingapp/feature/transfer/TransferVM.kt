package com.project.bankingapp.feature.transfer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.bankingapp.base.ScreenState
import com.project.bankingapp.base.onError
import com.project.bankingapp.base.onSuccess
import com.project.bankingapp.common.toDoubleAmount
import com.project.bankingapp.repository.BankingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransferVM @Inject constructor(
    private val repository: BankingRepository
) : ViewModel() {

    private val _transferResult = MutableLiveData<ScreenState<Unit>>()
    val transferResult: LiveData<ScreenState<Unit>> get() = _transferResult
    fun transfer(accountNo: String, amount: String, description: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            _transferResult.postValue(ScreenState.Loading)
            repository.transfer(accountNo, amount.toDoubleAmount(), description).onSuccess {
                _transferResult.postValue(ScreenState.Success(Unit))
            }.onError { _, exception ->
                _transferResult.postValue(ScreenState.Error(exception))
            }
        }
    }
}