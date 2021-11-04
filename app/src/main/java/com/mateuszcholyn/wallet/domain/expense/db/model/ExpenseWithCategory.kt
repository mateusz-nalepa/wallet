package com.mateuszcholyn.wallet.domain.expense.db.model

import androidx.room.Embedded
import com.mateuszcholyn.wallet.infrastructure.category.CategoryEntity

data class ExpenseWithCategory(

        @Embedded
        val expense: Expense,

        @Embedded
        val categoryEntity: CategoryEntity
)