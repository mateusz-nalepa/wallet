package com.mateuszcholyn.wallet.ui.screen.summary

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.mateuszcholyn.wallet.R
import com.mateuszcholyn.wallet.ui.dropdown.WalletDropdown

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SummaryCategoriesSection(
    summaryViewModel: SummaryViewModel = hiltViewModel()
) {
    WalletDropdown(
        dropdownName = stringResource(R.string.category),
        selectedElement = summaryViewModel.selectedCategory,
        availableElements = summaryViewModel.availableCategories,
        onItemSelected = { newSelectedCategory ->
            summaryViewModel.updateSelectedCategory(newSelectedCategory)
        },
    )
}