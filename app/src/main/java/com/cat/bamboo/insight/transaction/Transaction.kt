package com.cat.bamboo.insight.transaction


data class Transaction(
    val id: Int,
    val description: String,
    val amount: Double,
    val timestamp: Long = System.currentTimeMillis()
)