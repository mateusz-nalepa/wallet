package com.mateuszcholyn.wallet.ui.summary

import com.mateuszcholyn.wallet.domain.expense.Sort
import com.mateuszcholyn.wallet.scaffold.screens.fragments.DropdownElement

data class SortElement(
        override val name: String,
        val sort: Sort,
) : DropdownElement

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


    val sortingListBetter: List<SortElement>
        get() = sortingElements

}
