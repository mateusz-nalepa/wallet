package com.mateuszcholyn.wallet.backend.impl.infrastructure.sqlite.searchservice

import androidx.sqlite.db.SimpleSQLiteQuery
import com.mateuszcholyn.wallet.backend.api.core.category.CategoryId
import com.mateuszcholyn.wallet.backend.api.core.expense.ExpenseId
import com.mateuszcholyn.wallet.backend.api.searchservice.SearchCriteria
import com.mateuszcholyn.wallet.backend.impl.domain.searchservice.SearchServiceRepository
import com.mateuszcholyn.wallet.backend.impl.domain.searchservice.SearchSingleResultRepo

class SqLiteSearchServiceRepository(
    private val searchServiceDao: SearchServiceDao,
) : SearchServiceRepository {
    override fun getById(expenseId: ExpenseId): SearchSingleResultRepo? =
        searchServiceDao
            .findByExpenseId(expenseId.id)
            ?.toDomain()

    override fun saveExpense(searchSingleResultRepo: SearchSingleResultRepo): SearchSingleResultRepo =
        searchSingleResultRepo
            .toEntity()
            .also { searchServiceDao.save(it) }
            .toDomain()

    override fun getAll(searchCriteria: SearchCriteria): List<SearchSingleResultRepo> =
        searchServiceDao
            .getAll(SimpleSQLiteQuery(SearchServiceQueryHelper.prepareSearchQuery(searchCriteria)))
            .map { it.toDomain() }

    override fun remove(expenseId: ExpenseId) {
        searchServiceDao.remove(expenseId.id)
    }

    override fun removeAll() {
        searchServiceDao.removeAll()
    }
}


private fun SearchSingleResultRepo.toEntity(): SearchServiceEntity =
    SearchServiceEntity(
        expenseId = expenseId.id,
        categoryId = categoryId.id,
        amount = amount,
        paidAt = paidAt,
        description = description,
    )

private fun SearchServiceEntity.toDomain(): SearchSingleResultRepo =
    SearchSingleResultRepo(
        expenseId = ExpenseId(expenseId),
        categoryId = CategoryId(categoryId),
        amount = amount,
        paidAt = paidAt,
        description = description,
    )
