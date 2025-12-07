package com.cat.bamboo.insight.transaction

import java.text.NumberFormat
import java.time.LocalDate
import java.time.ZoneId
import java.util.Currency

object TransactionManager {
    private val transactions: MutableList<Transaction> = mutableListOf()

    val currencyFormat = NumberFormat.getCurrencyInstance().apply {
        maximumFractionDigits = 2
        currency = Currency.getInstance("INR")
    }

    fun addTransaction(transaction: Transaction) {
        transactions.add(transaction)
    }

    fun deleteTransaction(id: Int) {
        transactions.removeIf { transaction -> transaction.id == id }
    }

    fun getTransactions(): List<Transaction> {
        return transactions
    }

    fun getCurrentMonthTransactions(): List<Transaction> {
        val monthStart = LocalDate.now()
            .withDayOfMonth(1)
            .atStartOfDay(ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli()

        return transactions.filter { transaction -> transaction.timestamp > monthStart }
    }
}