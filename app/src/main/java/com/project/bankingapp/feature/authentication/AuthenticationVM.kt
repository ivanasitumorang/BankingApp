package com.project.bankingapp.feature.authentication

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
class AuthenticationVM @Inject constructor(
    private val repository: BankingRepository
) : ViewModel() {

    private val _loginResult = MutableLiveData<ScreenState<Unit>>()
    val loginResult: LiveData<ScreenState<Unit>> get() = _loginResult
    fun login(username: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _loginResult.postValue(ScreenState.Loading)
            repository.login(username, password).onSuccess {
                _loginResult.postValue(ScreenState.Success(Unit))
            }.onError { _, exception ->
                _loginResult.postValue(ScreenState.Error(exception))
            }
        }
    }

    private val _registerResult = MutableLiveData<ScreenState<Unit>>()
    val registerResult: LiveData<ScreenState<Unit>> get() = _registerResult
    fun register(username: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _registerResult.postValue(ScreenState.Loading)
            repository.register(username, password).onSuccess {
                _registerResult.postValue(ScreenState.Success(Unit))
            }.onError { _, exception ->
                _registerResult.postValue(ScreenState.Error(exception))
            }
        }
    }
}