package com.mateuszcholyn.wallet.ui.dropdown

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.mateuszcholyn.wallet.R
import com.mateuszcholyn.wallet.domain.expense.Sort

data class SortElement(
    override val name: String,
    val sort: Sort,
) : DropdownElement

@Composable
fun sortingElements(): List<SortElement> {
    return listOf(
        SortElement(
            name = stringResource(R.string.dataOdNajmlodszych),
            sort = Sort(Sort.Field.DATE, Sort.Type.DESC),
        ),
        SortElement(
            name = stringResource(R.string.dataOdNajstarszych),
            sort = Sort(Sort.Field.DATE, Sort.Type.ASC),
        ),
        SortElement(
            name = stringResource(R.string.cenaOdNajwyzszej),
            sort = Sort(Sort.Field.AMOUNT, Sort.Type.DESC),
        ),
        SortElement(
            name = stringResource(R.string.cenaOdNajnizszej),
            sort = Sort(Sort.Field.AMOUNT, Sort.Type.ASC),
        ),
    )
}
