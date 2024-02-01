package com.mateuszcholyn.wallet.frontend.view.screen.summary.filters

import androidx.compose.runtime.Composable
import com.mateuszcholyn.wallet.frontend.view.screen.summary.filters.advancedFilters.AdvancedFiltersSection

@Composable
fun SummaryFilters() {
    SummaryCategoriesSection()
    SummaryQuickDateRangeSection()
    AdvancedFiltersSection()
}