package com.mateuszcholyn.wallet.domain.expense.model

import org.joda.time.LocalDateTime

data class AverageSearchCriteria(
        val categoryName: String,
        val beginDate: LocalDateTime,
        val endDate: LocalDateTime
)

