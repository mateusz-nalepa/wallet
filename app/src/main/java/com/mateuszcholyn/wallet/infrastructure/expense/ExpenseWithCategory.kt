package com.mateuszcholyn.wallet.infrastructure.expense

import androidx.room.Embedded
import com.mateuszcholyn.wallet.infrastructure.category.CategoryEntity

data class ExpenseWithCategory(

    @Embedded
    val expenseEntity: ExpenseEntity,

    @Embedded
    val categoryEntity: CategoryEntity
)