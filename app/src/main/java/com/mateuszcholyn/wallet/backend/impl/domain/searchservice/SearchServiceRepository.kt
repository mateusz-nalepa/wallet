package com.mateuszcholyn.wallet.backend.impl.domain.searchservice

import com.mateuszcholyn.wallet.backend.api.core.category.CategoryId
import com.mateuszcholyn.wallet.backend.api.core.category.SubCategoryId
import com.mateuszcholyn.wallet.backend.api.core.expense.ExpenseId
import com.mateuszcholyn.wallet.backend.api.searchservice.NewSort
import com.mateuszcholyn.wallet.backend.api.searchservice.SearchCriteria
import java.math.BigDecimal
import java.time.Instant


data class SearchSingleResultRepo(
    val expenseId: ExpenseId,
    val categoryId: CategoryId,
    val subCategoryId: SubCategoryId?,
    val amount: BigDecimal,
    val paidAt: Instant,
    val description: String,
)

interface SearchServiceRepository {
    suspend fun getById(expenseId: ExpenseId): SearchSingleResultRepo?
    suspend fun saveExpense(searchSingleResultRepo: SearchSingleResultRepo): SearchSingleResultRepo
    suspend fun getAll(searchCriteria: SearchCriteria): List<SearchSingleResultRepo>
    suspend fun remove(expenseId: ExpenseId)
    suspend fun removeAll()
}

class InMemorySearchServiceRepository : SearchServiceRepository {
    private val storage: MutableMap<ExpenseId, SearchSingleResultRepo> = mutableMapOf()

    override suspend fun getById(expenseId: ExpenseId): SearchSingleResultRepo? =
        storage[expenseId]

    override suspend fun saveExpense(searchSingleResultRepo: SearchSingleResultRepo): SearchSingleResultRepo {
        storage[searchSingleResultRepo.expenseId] = searchSingleResultRepo
        return searchSingleResultRepo
    }

    override suspend fun getAll(searchCriteria: SearchCriteria): List<SearchSingleResultRepo> =
        storage
            .values
            .toList()
            .filterByBeginDate(searchCriteria)
            .filterByEndDate(searchCriteria)
            .filterByCategory(searchCriteria)
            .filterBySubCategory(searchCriteria)
            .filterByFromAmount(searchCriteria)
            .filterByToAmount(searchCriteria)
            .sort(searchCriteria)

    override suspend fun remove(expenseId: ExpenseId) {
        storage.remove(expenseId)
    }

    override suspend fun removeAll() {
        val ids = storage.values.toList().map { it.expenseId }

        ids.forEach {
            storage.remove(it)
        }
    }

    private fun List<SearchSingleResultRepo>.filterByBeginDate(searchCriteria: SearchCriteria): List<SearchSingleResultRepo> =
        if (searchCriteria.beginDate == null) {
            this
        } else {
            filter {
                it.paidAt.isAfter(searchCriteria.beginDate) || it.paidAt == searchCriteria.beginDate
            }
        }

    private fun List<SearchSingleResultRepo>.filterByEndDate(searchCriteria: SearchCriteria): List<SearchSingleResultRepo> =
        if (searchCriteria.endDate == null) {
            this
        } else {
            filter {
                it.paidAt.isBefore(searchCriteria.endDate) || it.paidAt == searchCriteria.endDate
            }
        }

    private fun List<SearchSingleResultRepo>.filterByCategory(searchCriteria: SearchCriteria): List<SearchSingleResultRepo> =
        if (searchCriteria.categoryId != null) {
            filter { it.categoryId == searchCriteria.categoryId }
        } else {
            this
        }

    private fun List<SearchSingleResultRepo>.filterBySubCategory(searchCriteria: SearchCriteria): List<SearchSingleResultRepo> =
        if (searchCriteria.subCategoryId != null) {
            filter { it.subCategoryId == searchCriteria.subCategoryId }
        } else {
            this
        }

    private fun List<SearchSingleResultRepo>.filterByFromAmount(searchCriteria: SearchCriteria): List<SearchSingleResultRepo> =
        if (searchCriteria.fromAmount != null) {
            filter { it.amount >= searchCriteria.fromAmount }
        } else {
            this
        }

    private fun List<SearchSingleResultRepo>.filterByToAmount(searchCriteria: SearchCriteria): List<SearchSingleResultRepo> =
        if (searchCriteria.toAmount != null) {
            filter { it.amount <= searchCriteria.toAmount }
        } else {
            this
        }

    private fun List<SearchSingleResultRepo>.sort(searchCriteria: SearchCriteria): List<SearchSingleResultRepo> {
        val sortedByField =
            when (searchCriteria.sort.field) {
                NewSort.Field.DATE -> sortedBy { it.paidAt }
                NewSort.Field.AMOUNT -> sortedBy { it.amount }
            }

        return when (searchCriteria.sort.order) {
            NewSort.Order.DESC -> sortedByField.reversed()
            NewSort.Order.ASC -> sortedByField
        }
    }
}