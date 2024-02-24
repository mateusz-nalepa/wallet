package com.mateuszcholyn.wallet.frontend.view.screen.history.filters

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import com.mateuszcholyn.wallet.frontend.view.screen.history.ExportToCsvUiState
import com.mateuszcholyn.wallet.frontend.view.screen.history.HistoryScreenActions
import com.mateuszcholyn.wallet.frontend.view.screen.history.HistorySearchForm
import com.mateuszcholyn.wallet.frontend.view.screen.history.filters.advancedOptions.AdvancedFiltersSectionStateless
import com.mateuszcholyn.wallet.frontend.view.util.defaultModifier

@Composable
fun HistoryFilters(
    historySearchForm: HistorySearchForm,
    historyScreenActions: HistoryScreenActions,
    exportUiState: ExportToCsvUiState,
) {
    val state = rememberScrollState()
    Column(modifier = defaultModifier.verticalScroll(state)) {
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
}