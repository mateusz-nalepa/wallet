package com.mateuszcholyn.wallet.frontend.view.screen.categoryScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.mateuszcholyn.wallet.R
import com.mateuszcholyn.wallet.frontend.view.util.defaultModifier

@Composable
fun NumberOfCategories(
    categorySuccessContent: CategorySuccessContent,
) {
    NumberOfCategoriesStateless(
        categoriesText = stringResource(R.string.categoryScreen_categories),
        categoriesQuantitySizeText = stringResource(R.string.common_quantity) + " ${categorySuccessContent.categoriesList.size}",
    )
}

@Composable
fun NumberOfCategoriesStateless(
    categoriesText: String,
    categoriesQuantitySizeText: String,
) {
    Row(modifier = defaultModifier, horizontalArrangement = Arrangement.SpaceBetween) {
        Text(
            text = categoriesText,
            modifier = defaultModifier.weight(1f)
        )
        Text(
            text = categoriesQuantitySizeText,
            modifier = defaultModifier.weight(1f)
        )
    }
}