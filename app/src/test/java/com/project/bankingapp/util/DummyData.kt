package com.project.bankingapp.util

import com.project.bankingapp.common.dto.Account
import com.project.bankingapp.feature.dashboard.dto.Transaction
import com.project.bankingapp.feature.dashboard.dto.TransactionHistory
import com.project.bankingapp.feature.dashboard.dto.TransactionType
import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import org.joda.time.format.DateTimeFormat

object DummyData {
    private val date = DateTime("2022-03-11T12:47:04.706Z", DateTimeZone.UTC)
    val transactions1 = listOf(
        Transaction(
            id = "1",
            amount = 1_000.0,
            date = date,
            type = TransactionType.Expense,
            account = Account(no = "8768-232-8233", name = "Dia")
        ),
        Transaction(
            id = "",
            amount = 2.0,
            date = date,
            type = TransactionType.Income,
            account = Account(no = "8999-324-9632", name = "Siapa")
        ),
        Transaction(
            id = "",
            amount = 30.0,
            date = date,
            type = TransactionType.Income,
            account = Account(no = "6554-630-9653", name = "Andy")
        )
    )

    val trxHistoryList = listOf(
        TransactionHistory(
            dateString = DateTimeFormat.forPattern("dd MMM YYYY").print(date),
            transactions = transactions1
        )
    )
}