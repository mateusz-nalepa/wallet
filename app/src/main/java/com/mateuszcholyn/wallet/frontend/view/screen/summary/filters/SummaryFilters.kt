package com.mateuszcholyn.wallet.frontend.view.screen.summary.filters

import androidx.compose.runtime.Composable
import com.mateuszcholyn.wallet.frontend.view.screen.summary.SummaryScreenActions
import com.mateuszcholyn.wallet.frontend.view.screen.summary.SummarySearchForm
import com.mateuszcholyn.wallet.frontend.view.screen.summary.filters.advancedFilters.AdvancedFiltersSectionStateless

@Composable
fun SummaryFilters(
    summarySearchForm: SummarySearchForm,
    summaryScreenActions: SummaryScreenActions,
) {
    SummaryCategoriesSection(
        categoriesList = summarySearchForm.categoriesList,
        selectedCategory = summarySearchForm.selectedCategory,
        onCategorySelected = summaryScreenActions.onCategorySelected,
    )
    SummaryQuickDateRangeSection(
        quickDateRanges = summarySearchForm.quickDataRanges,
        selectedQuickDateRange = summarySearchForm.selectedQuickRangeData,
        onQuickRangeDataSelected = summaryScreenActions.onQuickRangeDataSelected,
    )
    AdvancedFiltersSectionStateless(
        summarySearchForm = summarySearchForm,
        summaryScreenActions = summaryScreenActions,
    )
}