package com.mateuszcholyn.wallet.domain.expense

import java.io.Serializable
import java.time.LocalDateTime

data class ExpenseSearchCriteria(
        val allCategories: Boolean,
        val categoryId: Long? = null,
        val beginDate: LocalDateTime,
        val endDate: LocalDateTime,
        val fromAmount: Double = Double.MIN_VALUE,
        val toAmount: Double = Double.MAX_VALUE,
        val sort: Sort = Sort(Sort.Field.DATE, Sort.Type.DESC),
) : Serializable

data class Sort(
        val field: Field,
        val type: Type,
) {

    enum class Field {
        DATE,
        AMOUNT,
    }

    enum class Type {
        ASC,
        DESC,
    }

}
