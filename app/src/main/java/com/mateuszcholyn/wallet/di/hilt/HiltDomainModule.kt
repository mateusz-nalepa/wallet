package com.mateuszcholyn.wallet.di.hilt

import com.mateuszcholyn.wallet.domain.category.CategoryRepository
import com.mateuszcholyn.wallet.domain.category.CategoryService
import com.mateuszcholyn.wallet.domain.expense.ExpenseRepository
import com.mateuszcholyn.wallet.domain.expense.ExpenseService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HiltDomainModule {
    @Provides
    @Singleton
    fun provideCategoryService(categoryRepository: CategoryRepository): CategoryService =
            CategoryService(
                    categoryRepository = categoryRepository,
            )


    @Provides
    @Singleton
    fun provideExpenseService(expenseRepository: ExpenseRepository): ExpenseService =
            ExpenseService(
                    expenseRepository = expenseRepository,
            )
}