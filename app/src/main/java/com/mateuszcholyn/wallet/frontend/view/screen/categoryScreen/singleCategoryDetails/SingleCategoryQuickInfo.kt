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
import com.mateuszcholyn.wallet.backend.api.categoriesquicksummary.AbstractCategoryQuickSummary
import com.mateuszcholyn.wallet.frontend.view.screen.util.expandIcon.ExpandIcon
import com.mateuszcholyn.wallet.frontend.view.util.subCategoryPaddingModifier

@ExperimentalMaterialApi
@Composable
fun SingleCategoryQuickInfo(
    index: Int,
    categoryQuickSummary: AbstractCategoryQuickSummary,
    detailsAreVisible: Boolean,
    onClick: () -> Unit,
) {
    ListItem(
        modifier =
        Modifier
            .subCategoryPaddingModifier(categoryQuickSummary)
            .testTag("CategoryItem#${categoryQuickSummary.id.id}")
            .clickable {
                onClick.invoke()
            },

        text = { Text("$index. ${categoryQuickSummary.name}") },
        secondaryText = { Text(stringResource(R.string.categoryScreen_singleCategoryQuickInfoNumberOfExpenses) + " ${categoryQuickSummary.numberOfExpenses}") },
        trailing = {
            ExpandIcon(detailsAreVisible)
        }
    )

}
