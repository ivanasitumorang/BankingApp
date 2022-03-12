package com.project.bankingapp.common

import java.lang.Exception

fun String.toDoubleAmount(): Double {
    return try {
        toDouble()
    } catch (e: Exception) {
        0.0
    }
}