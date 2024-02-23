package com.mateuszcholyn.wallet.frontend.view.dropdown

import androidx.annotation.StringRes
import com.mateuszcholyn.wallet.R
import com.mateuszcholyn.wallet.backend.api.searchservice.NewSort


data class SortElement(
    override val name: String? = null,
    @StringRes
    override val nameKey: Int,
    val sort: NewSort,
) : DropdownElement {
    companion object {
        val default: SortElement =
            SortElement(
                nameKey = R.string.sort_data_fromTheYoungest,
                sort = NewSort(NewSort.Field.DATE, NewSort.Order.DESC),
            )
    }
}

fun sortingElements(): List<SortElement> =
    listOf(
        SortElement.default,
        SortElement(
            nameKey = R.string.sort_data_fromTheOldest,
            sort = NewSort(NewSort.Field.DATE, NewSort.Order.ASC),
        ),
        SortElement(
            nameKey = R.string.sort_price_fromTheHighest,
            sort = NewSort(NewSort.Field.AMOUNT, NewSort.Order.DESC),
        ),
        SortElement(
            nameKey = R.string.sort_price_fromTheLowest,
            sort = NewSort(NewSort.Field.AMOUNT, NewSort.Order.ASC),
        ),
    )
