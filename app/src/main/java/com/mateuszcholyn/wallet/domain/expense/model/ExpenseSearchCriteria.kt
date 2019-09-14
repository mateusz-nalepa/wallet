package com.mateuszcholyn.wallet.domain.expense.model

import org.joda.time.LocalDateTime
import java.io.Serializable

data class ExpenseSearchCriteria(
        val categoryName: String,
        val beginDate: LocalDateTime,
        val endDate: LocalDateTime
) : Serializable