package com.mateuszcholyn.wallet.frontend.view.dropdown

import com.mateuszcholyn.wallet.R
import com.mateuszcholyn.wallet.backend.api.searchservice.NewSort


data class SortElement(
    override val name: String,
    override val nameKey: Int? = null,
    val sort: NewSort,
) : DropdownElement {
    companion object {
        val default: SortElement =
            SortElement(
                name = "R.string.dataOdNajmlodszych",
                nameKey = R.string.dataOdNajmlodszych,
                sort = NewSort(NewSort.Field.DATE, NewSort.Order.DESC),
            )
    }
}

fun sortingElements(): List<SortElement> =
    listOf(
        SortElement.default,
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
