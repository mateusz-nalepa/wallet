package com.mateuszcholyn.wallet.frontend.view.screen.summary.filters

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.mateuszcholyn.wallet.R
import com.mateuszcholyn.wallet.frontend.view.dropdown.WalletDropdown

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SummaryCategoriesSection(
    categoriesList: List<CategoryView>,
    selectedCategory: CategoryView,
    onCategorySelected: (CategoryView) -> Unit,
) {
    WalletDropdown(
        dropdownName = stringResource(R.string.category),
        selectedElement = selectedCategory,
        availableElements = categoriesList,
        onItemSelected = { newSelectedCategory ->
            onCategorySelected.invoke(newSelectedCategory)
        },
    )
}
