package com.mateuszcholyn.wallet.ui.summary

import com.mateuszcholyn.wallet.domain.expense.Sort

data class SortElement(
        val name: String,
        val sort: Sort,
)

object SortingData {

    private val sortingElements = listOf(
            SortElement(
                    name = "data: od najmłodszych",
                    sort = Sort(Sort.Field.DATE, Sort.Type.DESC),
            ),
            SortElement(
                    name = "data: od najstarszych",
                    sort = Sort(Sort.Field.DATE, Sort.Type.ASC),
            ),
            SortElement(
                    name = "cena: od najwyższej",
                    sort = Sort(Sort.Field.AMOUNT, Sort.Type.DESC),
            ),
            SortElement(
                    name = "cena: od najniższej",
                    sort = Sort(Sort.Field.AMOUNT, Sort.Type.ASC),
            ),
    )

    val sortingList: List<String>
        get() = sortingElements.map { it.name }

    val sortingListBetter: List<SortElement>
        get() = sortingElements

    fun getSortByIndexName(index: Int): Sort =
            sortingElements[index].sort

}
