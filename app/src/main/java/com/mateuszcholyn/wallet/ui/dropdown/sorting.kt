package com.mateuszcholyn.wallet.ui.dropdown

import com.mateuszcholyn.wallet.R
import com.mateuszcholyn.wallet.domain.expense.Sort

data class SortElement(
    override val name: String,
    override val nameKey: Int? = null,
    val sort: Sort,
) : DropdownElement

fun sortingElements(): List<SortElement> {
    return listOf(
        SortElement(
            name = "R.string.dataOdNajmlodszych",
            nameKey = R.string.dataOdNajmlodszych,
            sort = Sort(Sort.Field.DATE, Sort.Type.DESC),
        ),
        SortElement(
            name = "R.string.dataOdNajstarszych",
            nameKey = R.string.dataOdNajstarszych,
            sort = Sort(Sort.Field.DATE, Sort.Type.ASC),
        ),
        SortElement(
            name = "R.string.cenaOdNajwyzszej",
            nameKey = R.string.cenaOdNajwyzszej,
            sort = Sort(Sort.Field.AMOUNT, Sort.Type.DESC),
        ),
        SortElement(
            name = "R.string.cenaOdNajnizszej",
            nameKey = R.string.cenaOdNajnizszej,
            sort = Sort(Sort.Field.AMOUNT, Sort.Type.ASC),
        ),
    )
}
