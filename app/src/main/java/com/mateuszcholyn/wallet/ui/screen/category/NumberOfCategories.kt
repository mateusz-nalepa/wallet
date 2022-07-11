package com.mateuszcholyn.wallet.ui.screen.category

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.mateuszcholyn.wallet.R
import com.mateuszcholyn.wallet.ui.util.defaultModifier

@Composable
fun NumberOfCategories(
    categoryViewModel: CategoryViewModel = hiltViewModel()
) {
    NumberOfCategoriesStateless(categoryViewModel.categoryOptions.size)
}

@Composable
fun NumberOfCategoriesStateless(
    numberOfCategories: Int,
) {
    Row(modifier = defaultModifier, horizontalArrangement = Arrangement.SpaceBetween) {
        Text(text = stringResource(R.string.categories), modifier = defaultModifier.weight(1f))
        Text(
            text = stringResource(R.string.quantity) + " $numberOfCategories",
            modifier = defaultModifier.weight(1f)
        )
    }
}