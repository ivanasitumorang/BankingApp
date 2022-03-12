package com.project.bankingapp.base

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys

abstract class BaseSharedPreferences(private val context: Context) {

    abstract val fileName: String

    private val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)

    protected val sharedPreferences: SharedPreferences by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            EncryptedSharedPreferences.create(
                fileName,
                masterKeyAlias,
                context,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )
        else context.getSharedPreferences(fileName, Context.MODE_PRIVATE)
    }

    protected fun _setString(key: String, value: String) {
        sharedPreferences.edit().apply {
            putString(key, value)
        }.apply()
    }

    protected fun _getString(key: String, defValue: String = ""): String {
        return sharedPreferences.getString(key, defValue) ?: defValue
    }

    protected fun _setBoolean(key: String, value: Boolean) {
        sharedPreferences.edit().apply {
            putBoolean(key, value)
        }.apply()
    }

    protected fun _getBoolean(key: String, defValue: Boolean = false): Boolean {
        return sharedPreferences.getBoolean(key, defValue)
    }

    protected fun _setInt(key: String, value: Int) {
        sharedPreferences.edit().apply {
            putInt(key, value)
        }.apply()
    }

    protected fun _getInt(key: String, defValue: Int = 0): Int {
        return sharedPreferences.getInt(key, defValue)
    }

    fun clearAll() {
        sharedPreferences.edit().clear().apply()
    }

}