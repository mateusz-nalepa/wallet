package com.mateuszcholyn.wallet.frontend.view.screen.history.filters

import androidx.compose.runtime.Composable
import com.mateuszcholyn.wallet.frontend.view.screen.history.ExportToCsvUiState
import com.mateuszcholyn.wallet.frontend.view.screen.history.HistoryScreenActions
import com.mateuszcholyn.wallet.frontend.view.screen.history.HistorySearchForm
import com.mateuszcholyn.wallet.frontend.view.screen.history.filters.advancedOptions.AdvancedFiltersSectionStateless

@Composable
fun HistoryFilters(
    historySearchForm: HistorySearchForm,
    historyScreenActions: HistoryScreenActions,
    exportUiState: ExportToCsvUiState,
) {
    HistoryCategoriesSection(
        categoriesList = historySearchForm.categoriesList,
        selectedCategory = historySearchForm.selectedCategory,
        onCategorySelected = historyScreenActions.onCategorySelected,
    )
    HistoryQuickDateRangeSection(
        historySearchForm = historySearchForm,
        historyScreenActions = historyScreenActions,
    )
    AdvancedFiltersSectionStateless(
        historySearchForm = historySearchForm,
        historyScreenActions = historyScreenActions,
        exportUiState = exportUiState,
    )
}