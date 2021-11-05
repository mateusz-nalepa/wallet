package com.mateuszcholyn.wallet.domain.expense

import java.time.LocalDateTime


data class AverageSearchCriteria(
    val allCategories: Boolean,
    val categoryName: String?,
    val beginDate: LocalDateTime,
    val endDate: LocalDateTime
)

