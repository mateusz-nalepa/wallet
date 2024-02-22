package com.mateuszcholyn.wallet.frontend.view.screen.history.filters

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.mateuszcholyn.wallet.R
import com.mateuszcholyn.wallet.frontend.view.dropdown.WalletDropdown

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HistoryCategoriesSection(
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
