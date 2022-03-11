package com.project.bankingapp.feature.dashboard

import org.joda.time.DateTime

data class Transaction(
    val id: String,
    val amount: Double,
    val date: DateTime,
    val type: TransactionType,
    val recipientName: Recipient
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

val transactions1 = listOf(
    Transaction(
        id = "1",
        amount = 1_000.0,
        date = DateTime("2022-03-11T11:47:04.706Z"),
        type = TransactionType.Expense,
        recipientName = Recipient(accountNo = "8768-232-8233", accountHolder = "Dia")
    ),
    Transaction(
        id = "",
        amount = 2.0,
        date = DateTime("2022-03-11T12:47:04.706Z"),
        type = TransactionType.Income,
        recipientName = Recipient(accountNo = "8999-324-9632", accountHolder = "Siapa")
    ),
    Transaction(
        id = "",
        amount = 30.0,
        date = DateTime("2022-03-11T08:47:04.706Z"),
        type = TransactionType.Income,
        recipientName = Recipient(accountNo = "6554-630-9653", accountHolder = "Andy")
    )
)

val transactions2 = listOf(
    Transaction(
        id = "1",
        amount = 20_000.0,
        date = DateTime("2022-05-11T11:47:04.706Z"),
        type = TransactionType.Expense,
        recipientName = Recipient(accountNo = "8768-232-8233", accountHolder = "Dia")
    ),
    Transaction(
        id = "",
        amount = 50.0,
        date = DateTime("2022-05-11T12:47:04.706Z"),
        type = TransactionType.Expense,
        recipientName = Recipient(accountNo = "8999-324-9632", accountHolder = "Siapa")
    )
)

val trxHistory1 = listOf(
    TransactionHistory(
        dateString = DateTime("2022-03-11T12:47:04.706Z").toString("DD MMM YYYY"),
        transactions = transactions1
    )
)

val trxHistory2 = listOf(
    TransactionHistory(
        dateString = DateTime("2022-03-11T12:47:04.706Z").toString("DD MMM YYYY"),
        transactions = transactions2
    )
)