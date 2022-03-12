package com.project.bankingapp.base

sealed class ScreenState<out R> {
    object Loading : ScreenState<Nothing>()
    data class Success<out T>(val data: T) : ScreenState<T>()
    data class Error(val exception: Exception) : ScreenState<Nothing>()
}