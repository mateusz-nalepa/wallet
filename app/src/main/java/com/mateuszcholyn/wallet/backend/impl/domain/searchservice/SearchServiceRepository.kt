package com.mateuszcholyn.wallet.backend.impl.domain.searchservice

import com.mateuszcholyn.wallet.backend.api.core.category.CategoryId
import com.mateuszcholyn.wallet.backend.api.core.expense.ExpenseId
import com.mateuszcholyn.wallet.backend.api.searchservice.NewSort
import com.mateuszcholyn.wallet.backend.api.searchservice.SearchCriteria
import java.math.BigDecimal
import java.time.LocalDateTime


data class SearchSingleResultRepo(
    val expenseId: ExpenseId,
    val categoryId: CategoryId,
    val amount: BigDecimal,
    val paidAt: LocalDateTime,
    val description: String,
)

interface SearchServiceRepository {
    fun getById(expenseId: ExpenseId): SearchSingleResultRepo?
    fun saveExpense(searchSingleResultRepo: SearchSingleResultRepo): SearchSingleResultRepo
    fun getAll(searchCriteria: SearchCriteria): List<SearchSingleResultRepo>
    fun remove(expenseId: ExpenseId)
    fun removeAll()
}

class InMemorySearchServiceRepository : SearchServiceRepository {
    private val storage: MutableMap<ExpenseId, SearchSingleResultRepo> = mutableMapOf()

    override fun getById(expenseId: ExpenseId): SearchSingleResultRepo? =
        storage[expenseId]

    override fun saveExpense(searchSingleResultRepo: SearchSingleResultRepo): SearchSingleResultRepo {
        storage[searchSingleResultRepo.expenseId] = searchSingleResultRepo
        return searchSingleResultRepo
    }

    override fun getAll(searchCriteria: SearchCriteria): List<SearchSingleResultRepo> =
        storage
            .values
            .toList()
            .filterByBeginDate(searchCriteria)
            .filterByEndDate(searchCriteria)
            .filterByCategory(searchCriteria)
            .filterByFromAmount(searchCriteria)
            .filterByToAmount(searchCriteria)
            .sort(searchCriteria)

    override fun remove(expenseId: ExpenseId) {
        storage.remove(expenseId)
    }

    override fun removeAll() {
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
                it.paidAt.isAfter(searchCriteria.beginDate) || it.paidAt.isEqual(searchCriteria.beginDate)
            }
        }

    private fun List<SearchSingleResultRepo>.filterByEndDate(searchCriteria: SearchCriteria): List<SearchSingleResultRepo> =
        if (searchCriteria.endDate == null) {
            this
        } else {
            filter {
                it.paidAt.isBefore(searchCriteria.endDate) || it.paidAt.isEqual(searchCriteria.endDate)
            }
        }

    private fun List<SearchSingleResultRepo>.filterByCategory(searchCriteria: SearchCriteria): List<SearchSingleResultRepo> =
        if (searchCriteria.categoryId != null) {
            filter { it.categoryId == searchCriteria.categoryId }
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