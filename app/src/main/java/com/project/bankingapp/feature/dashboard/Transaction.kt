package com.project.bankingapp.feature.dashboard

import org.joda.time.DateTime

data class Transaction(
    val id: String,
    val amount: Double,
    val date: DateTime,
    val type: TransactionType,
    val recipientName: Recipient
)

enum class TransactionType {
    Income, Expense
}
