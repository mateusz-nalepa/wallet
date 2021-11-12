package com.mateuszcholyn.wallet.domain.expense

import com.mateuszcholyn.wallet.util.maxDate
import com.mateuszcholyn.wallet.util.minDate
import java.io.Serializable
import java.time.LocalDateTime

data class ExpenseSearchCriteria(
    val allCategories: Boolean,
    val categoryName: String?,
    val beginDate: LocalDateTime,
    val endDate: LocalDateTime
) : Serializable {

    companion object {
        fun defaultSearchCriteria(): ExpenseSearchCriteria {
            return ExpenseSearchCriteria(
                allCategories = true,
                categoryName = null,
                beginDate = minDate,
                endDate = maxDate
            )
        }

        fun defaultSearchCriteria(beginDate: LocalDateTime,
                                  endDate: LocalDateTime,
        ): ExpenseSearchCriteria {
            return ExpenseSearchCriteria(
                allCategories = true,
                categoryName = null,
                beginDate = beginDate,
                endDate = endDate
            )
        }

    }

}