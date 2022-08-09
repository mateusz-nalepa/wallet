package com.mateuszcholyn.wallet.newcode.app.backend.categoriesquicksummary

import com.mateuszcholyn.wallet.newcode.app.backend.core.category.CategoryId
import com.mateuszcholyn.wallet.newcode.app.backend.events.*


class CategoriesQuickSummaryIMPL(
    private val categoriesQuickSummaryRepository: CategoriesQuickSummaryRepository,
) : CategoriesQuickSummaryAPI {
    override fun handleCategoryAdded(categoryAddedEvent: CategoryAddedEvent) {
        categoryAddedEvent
            .toQuickSummary()
            .also { categoriesQuickSummaryRepository.saveQuickSummary(it) }
    }

    override fun handleCategoryRemoved(categoryRemovedEvent: CategoryRemovedEvent) {
        categoriesQuickSummaryRepository.remove(categoryRemovedEvent.categoryId)
    }

    override fun handleEventExpenseAdded(expenseAddedEvent: ExpenseAddedEvent) {
        incWithOne(expenseAddedEvent.categoryId)
    }

    override fun handleEventExpenseUpdated(expenseUpdatedEvent: ExpenseUpdatedEvent) {
        decWithOne(expenseUpdatedEvent.oldCategoryId)
        incWithOne(expenseUpdatedEvent.newCategoryId)
    }

    override fun getQuickSummary(): QuickSummaryList =
        categoriesQuickSummaryRepository
            .getQuickSummaries()
            .let { QuickSummaryList(it) }

    override fun handleEventExpenseRemoved(expenseRemovedEvent: ExpenseRemovedEvent) {
        decWithOne(expenseRemovedEvent.categoryId)
    }

    private fun CategoryAddedEvent.toQuickSummary(): QuickSummary =
        QuickSummary(
            categoryId = categoryId,
            numberOfExpenses = 0,
        )

    private fun incWithOne(categoryId: CategoryId) {
        categoriesQuickSummaryRepository
            .findByCategoryIdOrThrow(categoryId)
            .incWithOne()
            .also { categoriesQuickSummaryRepository.saveQuickSummary(it) }
    }

    private fun decWithOne(categoryId: CategoryId) {
        categoriesQuickSummaryRepository
            .findByCategoryIdOrThrow(categoryId)
            .decWithOne()
            .also { categoriesQuickSummaryRepository.saveQuickSummary(it) }
    }

}

private fun CategoriesQuickSummaryRepository.findByCategoryIdOrThrow(categoryId: CategoryId) =
    this.findByCategoryId(categoryId)
        ?: throw CategoryQuickSummaryNotFoundException(categoryId)


class CategoryQuickSummaryNotFoundException(categoryId: CategoryId) :
    RuntimeException("Category quick summary for category with id ${categoryId.id} does not exist")
