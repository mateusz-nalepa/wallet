package com.mateuszcholyn.wallet.frontend.view.screen.summary

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.mateuszcholyn.wallet.R
import com.mateuszcholyn.wallet.frontend.view.dropdown.WalletDropdown

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SummaryCategoriesSection(
    summaryViewModel: SummaryViewModel = hiltViewModel()
) {
    val availableCategories by remember { mutableStateOf(summaryViewModel.readCategoriesList()) }

    WalletDropdown(
        dropdownName = stringResource(R.string.category),
        selectedElement = summaryViewModel.summarySearchForm.selectedCategory,
        availableElements = availableCategories,
        onItemSelected = { newSelectedCategory ->
            summaryViewModel.updateSelectedCategory(newSelectedCategory)
        },
    )
}