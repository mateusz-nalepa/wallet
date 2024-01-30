package com.mateuszcholyn.wallet.backend.impl.infrastructure.sqlite.searchservice

import androidx.sqlite.db.SimpleSQLiteQuery
import com.mateuszcholyn.wallet.backend.api.core.category.CategoryId
import com.mateuszcholyn.wallet.backend.api.core.expense.ExpenseId
import com.mateuszcholyn.wallet.backend.api.searchservice.SearchCriteria
import com.mateuszcholyn.wallet.backend.impl.domain.searchservice.SearchServiceRepository
import com.mateuszcholyn.wallet.backend.impl.domain.searchservice.SearchSingleResultRepo
import com.mateuszcholyn.wallet.backend.impl.infrastructure.coroutineDispatcher.DispatcherProvider
import kotlinx.coroutines.withContext

class SqLiteSearchServiceRepository(
    private val searchServiceDao: SearchServiceDao,
    private val dispatcherProvider: DispatcherProvider,
) : SearchServiceRepository {
    override suspend fun getById(expenseId: ExpenseId): SearchSingleResultRepo? =
        withContext(dispatcherProvider.provideIODispatcher()) {
            searchServiceDao
                .findByExpenseId(expenseId.id)
                ?.toDomain()
        }

    override suspend fun saveExpense(searchSingleResultRepo: SearchSingleResultRepo): SearchSingleResultRepo =
        withContext(dispatcherProvider.provideIODispatcher()) {
            searchSingleResultRepo
                .toEntity()
                .also { searchServiceDao.save(it) }
                .toDomain()
        }

    override suspend fun getAll(searchCriteria: SearchCriteria): List<SearchSingleResultRepo> =
        withContext(dispatcherProvider.provideIODispatcher()) {
            searchServiceDao
                .getAll(SimpleSQLiteQuery(SearchServiceQueryHelper.prepareSearchQuery(searchCriteria)))
                .map { it.toDomain() }
        }

    override suspend fun remove(expenseId: ExpenseId) {
        withContext(dispatcherProvider.provideIODispatcher()) {
            searchServiceDao.remove(expenseId.id)
        }
    }

    override suspend fun removeAll() {
        withContext(dispatcherProvider.provideIODispatcher()) {
            searchServiceDao.removeAll()
        }
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
