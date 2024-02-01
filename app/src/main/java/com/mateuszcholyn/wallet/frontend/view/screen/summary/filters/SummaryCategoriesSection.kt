package com.mateuszcholyn.wallet.frontend.view.screen.summary.filters

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.mateuszcholyn.wallet.R
import com.mateuszcholyn.wallet.frontend.view.dropdown.WalletDropdown
import com.mateuszcholyn.wallet.frontend.view.screen.summary.SummaryScreenViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SummaryCategoriesSection(
    summaryScreenViewModel: SummaryScreenViewModel = hiltViewModel()
) {
    val availableCategories by remember { summaryScreenViewModel.categoriesList }

    WalletDropdown(
        dropdownName = stringResource(R.string.category),
        selectedElement = summaryScreenViewModel.summarySearchForm.selectedCategory,
        availableElements = availableCategories,
        onItemSelected = { newSelectedCategory ->
            summaryScreenViewModel.updateSelectedCategory(newSelectedCategory)
        },
    )
}
