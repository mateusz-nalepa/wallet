package com.mateuszcholyn.wallet.domain.expense.model

import com.mateuszcholyn.wallet.domain.category.model.CategoryDto
import org.joda.time.LocalDateTime
import java.io.Serializable

data class ExpenseDto(
        var id: Long = -1,

        val amount: Double,
        val date: LocalDateTime,
        val description: String,

        val category: CategoryDto
) : Serializable