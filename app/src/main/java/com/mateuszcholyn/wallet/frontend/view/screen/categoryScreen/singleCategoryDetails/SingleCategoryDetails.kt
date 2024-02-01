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
import com.mateuszcholyn.wallet.frontend.view.screen.categoryScreen.CategorySuccessContent
import com.mateuszcholyn.wallet.frontend.view.screen.categoryScreen.singleCategoryDetails.actions.edit.EditCategoryForm
import com.mateuszcholyn.wallet.frontend.view.screen.categoryScreen.singleCategoryDetails.actions.edit.EditSingleCategoryIconButton
import com.mateuszcholyn.wallet.frontend.view.screen.categoryScreen.singleCategoryDetails.actions.remove.RemoveSingleCategoryIconButton
import com.mateuszcholyn.wallet.frontend.view.util.defaultModifier


@ExperimentalMaterialApi
@Composable
fun SingleCategory(
    refreshScreenFunction: () -> Unit,
    categoryQuickSummary: CategoryQuickSummary,
    categorySuccessContent: CategorySuccessContent,
) {
    var detailsAreVisible by remember { mutableStateOf(false) }
    var editCategoryNameIsVisible by remember { mutableStateOf(false) }

    SingleCategoryQuickInfo(
        categoryQuickSummary = categoryQuickSummary,
        detailsAreVisible = detailsAreVisible,
        onClick = {
            detailsAreVisible = !detailsAreVisible
        }
    )

    if (detailsAreVisible) {
        Row(
            modifier = defaultModifier.padding(top = 0.dp),
            horizontalArrangement = Arrangement.End
        ) {
            // refreshScreen?
            EditSingleCategoryIconButton(
                onClick = {
                    editCategoryNameIsVisible = !editCategoryNameIsVisible
                },
            )
            // refreshScreen?
            RemoveSingleCategoryIconButton(
                categoryQuickSummary = categoryQuickSummary,
                refreshScreenFunction = refreshScreenFunction,
            )

        }
        if (editCategoryNameIsVisible) {
            EditCategoryForm(
                categoryQuickSummary = categoryQuickSummary,
                actualCategoryName = categoryQuickSummary.categoryName,
                onFormSubmitted = {
                    editCategoryNameIsVisible = false
                    detailsAreVisible = false
                },
                categorySuccessContent = categorySuccessContent,
                refreshScreenFunction = refreshScreenFunction,
            )
        }
    }

    Divider()
}
