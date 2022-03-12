package com.project.bankingapp.feature.dashboard.dto

import org.joda.time.DateTime

data class Transaction(
    val id: String,
    val amount: Double,
    val date: DateTime,
    val type: TransactionType,
    val account: Account
) {
//    data class Recipient(
//        val accountNo: String,
//        val accountHolder: String
//    )
}

enum class TransactionType {
    Income, Expense
}

data class TransactionHistory(
    val dateString: String,
    val transactions: List<Transaction>
)