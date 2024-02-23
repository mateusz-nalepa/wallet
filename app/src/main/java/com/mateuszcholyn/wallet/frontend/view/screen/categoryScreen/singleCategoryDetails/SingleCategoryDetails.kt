package com.mateuszcholyn.wallet.frontend.view.screen.categoryScreen.singleCategoryDetails

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import com.mateuszcholyn.wallet.backend.api.categoriesquicksummary.CategoryQuickSummary
import com.mateuszcholyn.wallet.frontend.view.screen.categoryScreen.CategoryScreenActions
import com.mateuszcholyn.wallet.frontend.view.screen.categoryScreen.RemoveCategoryState
import com.mateuszcholyn.wallet.frontend.view.screen.categoryScreen.singleCategoryDetails.actions.edit.EditSingleCategoryIconButton
import com.mateuszcholyn.wallet.frontend.view.screen.categoryScreen.singleCategoryDetails.actions.remove.RemoveSingleCategoryIconButton
import com.mateuszcholyn.wallet.frontend.view.util.defaultModifier


@ExperimentalMaterialApi
@Composable
fun SingleCategory(
    index: Int,
    categoryScreenActions: CategoryScreenActions,
    categoryQuickSummary: CategoryQuickSummary,
    removeCategoryState: RemoveCategoryState,
) {
    var detailsAreVisible by remember { mutableStateOf(false) }

    SingleCategoryQuickInfo(
        index = index,
        categoryQuickSummary = categoryQuickSummary,
        detailsAreVisible = detailsAreVisible,
        onClick = {
            detailsAreVisible = !detailsAreVisible
        }
    )

    if (detailsAreVisible) {
        SingleCategoryDetails(
            removeCategoryState = removeCategoryState,
            categoryScreenActions = categoryScreenActions,
            categoryQuickSummary = categoryQuickSummary,
        )
    }
    Divider()
}

@ExperimentalMaterialApi
@Composable
fun SingleCategoryDetails(
    categoryScreenActions: CategoryScreenActions,
    categoryQuickSummary: CategoryQuickSummary,
    removeCategoryState: RemoveCategoryState,
) {
    Row(
        modifier = defaultModifier.padding(top = 0.dp),
        horizontalArrangement = Arrangement.End
    ) {
        CategoryActionsStateless(
            categoryScreenActions = categoryScreenActions,
            categoryQuickSummary = categoryQuickSummary,
            removeCategoryState = removeCategoryState,
        )
    }
}

@Composable
fun CategoryActionsStateless(
    categoryScreenActions: CategoryScreenActions,
    categoryQuickSummary: CategoryQuickSummary,
    removeCategoryState: RemoveCategoryState,
) {
    EditSingleCategoryIconButton(
        categoryQuickSummary = categoryQuickSummary,
        categoryScreenActions = categoryScreenActions,
    )
    RemoveSingleCategoryIconButton(
        removeCategoryState = removeCategoryState,
        categoryQuickSummary = categoryQuickSummary,
        categoryScreenActions = categoryScreenActions,
    )
}
