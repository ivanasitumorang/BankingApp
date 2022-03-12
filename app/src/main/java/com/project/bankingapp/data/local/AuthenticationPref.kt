package com.project.bankingapp.data.local

import android.content.Context
import com.project.bankingapp.base.BaseSharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthenticationPref @Inject constructor(
    @ApplicationContext private val context: Context
) : BaseSharedPreferences(context) {
    override val fileName: String
        get() = FILE_NAME

    companion object {
        private const val FILE_NAME = "pref_authentication"
        private const val KEY_TOKEN = "key_token"
    }

    fun getToken(): String = _getString(KEY_TOKEN, "")
    fun setToken(token: String) = _setString(KEY_TOKEN, token)
}