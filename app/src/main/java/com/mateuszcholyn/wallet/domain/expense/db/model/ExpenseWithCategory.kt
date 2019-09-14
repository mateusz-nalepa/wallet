package com.mateuszcholyn.wallet.domain.expense.db.model

import android.arch.persistence.room.Embedded
import com.mateuszcholyn.wallet.domain.category.db.model.Category

data class ExpenseWithCategory(

        @Embedded
        val expense: Expense,

        @Embedded
        val category: Category
)