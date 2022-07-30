package com.mateuszcholyn.wallet.ui.screen.summary.advancedFilters

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import com.mateuszcholyn.wallet.R
import com.mateuszcholyn.wallet.ui.util.defaultModifier

@Composable
fun AdvancedFiltersSection() {
    var advancedFiltersExpanded by remember { mutableStateOf(false) }

    Row(
        modifier = defaultModifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {

        ClickableText(
            text = if (advancedFiltersExpanded) AnnotatedString(stringResource(R.string.hideFilters)) else AnnotatedString(
                stringResource(R.string.showFilters)
            ),
            onClick = {
                advancedFiltersExpanded = !advancedFiltersExpanded
            },
        )
        Text(
            text =
            if (advancedFiltersExpanded)
                stringResource(R.string.hideFilters)
            else
                stringResource(R.string.showFilters),
        )
        Checkbox(checked = advancedFiltersExpanded, onCheckedChange = {
            advancedFiltersExpanded = !advancedFiltersExpanded
        })
    }
    if (advancedFiltersExpanded) {
        SummarySortingSection()
        SummaryGroupingSection()
        AmountRangeSection()
    }
}