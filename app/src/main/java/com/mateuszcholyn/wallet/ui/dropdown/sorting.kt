package com.mateuszcholyn.wallet.ui.dropdown

import com.mateuszcholyn.wallet.R
import com.mateuszcholyn.wallet.newcode.app.backend.searchservice.NewSort

data class SortElement(
    override val name: String,
    override val nameKey: Int? = null,
    val sort: NewSort,
) : DropdownElement

fun sortingElements(): List<SortElement> =
    listOf(
        SortElement(
            name = "R.string.dataOdNajmlodszych",
            nameKey = R.string.dataOdNajmlodszych,
            sort = NewSort(NewSort.Field.DATE, NewSort.Order.DESC),
        ),
        SortElement(
            name = "R.string.dataOdNajstarszych",
            nameKey = R.string.dataOdNajstarszych,
            sort = NewSort(NewSort.Field.DATE, NewSort.Order.ASC),
        ),
        SortElement(
            name = "R.string.cenaOdNajwyzszej",
            nameKey = R.string.cenaOdNajwyzszej,
            sort = NewSort(NewSort.Field.AMOUNT, NewSort.Order.DESC),
        ),
        SortElement(
            name = "R.string.cenaOdNajnizszej",
            nameKey = R.string.cenaOdNajnizszej,
            sort = NewSort(NewSort.Field.AMOUNT, NewSort.Order.ASC),
        ),
    )
