package com.mateuszcholyn.wallet.domain.expense

import java.io.Serializable
import java.time.LocalDateTime

data class ExpenseSearchCriteria(
    val categoryName: String,
    val beginDate: LocalDateTime,
    val endDate: LocalDateTime
) : Serializable