package com.mateuszcholyn.wallet.domain.expense

import org.joda.time.LocalDateTime

data class AverageSearchCriteria(
    val categoryName: String,
    val beginDate: LocalDateTime,
    val endDate: LocalDateTime
)

