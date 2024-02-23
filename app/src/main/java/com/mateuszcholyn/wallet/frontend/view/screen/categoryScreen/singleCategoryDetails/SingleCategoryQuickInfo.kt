package com.mateuszcholyn.wallet.frontend.view.screen.categoryScreen.singleCategoryDetails

import androidx.compose.foundation.clickable
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ListItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import com.mateuszcholyn.wallet.R
import com.mateuszcholyn.wallet.backend.api.categoriesquicksummary.CategoryQuickSummary

@ExperimentalMaterialApi
@Composable
fun SingleCategoryQuickInfo(
    index: Int,
    categoryQuickSummary: CategoryQuickSummary,
    detailsAreVisible: Boolean,
    onClick: () -> Unit,
) {
    ListItem(
        modifier =
        Modifier
            .testTag("CategoryItem#${categoryQuickSummary.categoryId.id}")
            .clickable {
                onClick.invoke()
            },

        text = { Text("$index. ${categoryQuickSummary.categoryName}") },
        secondaryText = { Text(stringResource(R.string.amountOfExpenses) + " ${categoryQuickSummary.numberOfExpenses}") },
        trailing = {
            SingleCategoryTrailingIcon(detailsAreVisible)
        }
    )

}
