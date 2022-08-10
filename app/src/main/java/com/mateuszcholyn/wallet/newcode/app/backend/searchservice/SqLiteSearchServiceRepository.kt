package com.mateuszcholyn.wallet.newcode.app.backend.searchservice

import androidx.sqlite.db.SimpleSQLiteQuery
import com.mateuszcholyn.wallet.config.newDatabase.SearchServiceDao
import com.mateuszcholyn.wallet.config.newDatabase.SearchServiceEntity
import com.mateuszcholyn.wallet.newcode.app.backend.core.category.CategoryId
import com.mateuszcholyn.wallet.newcode.app.backend.core.expense.ExpenseId
import com.mateuszcholyn.wallet.newcode.app.backend.events.ExpenseAddedEvent

class SqLiteSearchServiceRepository(
    private val searchServiceDao: SearchServiceDao,
) : SearchServiceRepository {
    override fun getById(expenseId: ExpenseId): ExpenseAddedEvent? =
        searchServiceDao
            .findByExpenseId(expenseId.id)
            ?.toDomain()

    override fun saveExpense(expenseAddedEvent: ExpenseAddedEvent): ExpenseAddedEvent =
        expenseAddedEvent
            .toEntity()
            .also { searchServiceDao.save(it) }
            .toDomain()

    override fun getAll(searchCriteria: SearchCriteria): List<ExpenseAddedEvent> =
        searchServiceDao
            .getAll(SimpleSQLiteQuery(SearchServiceQueryHelper.prepareSearchQuery(searchCriteria)))
            .map { it.toDomain() }

    override fun remove(expenseId: ExpenseId) {
        searchServiceDao.remove(expenseId.id)
    }
}


private fun ExpenseAddedEvent.toEntity(): SearchServiceEntity =
    SearchServiceEntity(
        expenseId = expenseId.id,
        categoryId = categoryId.id,
        amount = amount,
        paidAt = paidAt,
    )

private fun SearchServiceEntity.toDomain(): ExpenseAddedEvent =
    ExpenseAddedEvent(
        expenseId = ExpenseId(expenseId),
        categoryId = CategoryId(categoryId),
        amount = amount,
        paidAt = paidAt,
    )
