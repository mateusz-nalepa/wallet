package com.mateuszcholyn.wallet.frontend.view.screen.categoryScreen.singleCategoryDetails

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Article
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.mateuszcholyn.wallet.R
import com.mateuszcholyn.wallet.backend.api.categoriesquicksummary.AbstractCategoryQuickSummary
import com.mateuszcholyn.wallet.backend.api.categoriesquicksummary.MainCategoryQuickSummary
import com.mateuszcholyn.wallet.frontend.view.screen.categoryScreen.CategoryScreenActions
import com.mateuszcholyn.wallet.frontend.view.screen.categoryScreen.NumberOfCategoriesStateless
import com.mateuszcholyn.wallet.frontend.view.screen.categoryScreen.RemoveCategoryState
import com.mateuszcholyn.wallet.frontend.view.screen.categoryScreen.singleCategoryDetails.actions.edit.EditSingleCategoryIconButton
import com.mateuszcholyn.wallet.frontend.view.screen.categoryScreen.singleCategoryDetails.actions.remove.RemoveSingleCategoryIconButton
import com.mateuszcholyn.wallet.frontend.view.util.currentAppContext
import com.mateuszcholyn.wallet.frontend.view.util.defaultModifier
import com.mateuszcholyn.wallet.frontend.view.util.showLongText
import com.mateuszcholyn.wallet.frontend.view.util.subCategoryStartPadding


@ExperimentalMaterialApi
@Composable
fun SingleCategory(
    index: Int,
    categoryScreenActions: CategoryScreenActions,
    categoryQuickSummary: AbstractCategoryQuickSummary,
    removeCategoryState: RemoveCategoryState,
) {
    val context = currentAppContext()

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
        if (categoryQuickSummary is MainCategoryQuickSummary) {
            if (categoryQuickSummary.subCategories.isNotEmpty()) {
                Row(
                    modifier = Modifier.padding(start = subCategoryStartPadding)
                ) {
                    NumberOfCategoriesStateless(
                        // TODO: translate me
                        categoriesText = "Subcategories",
                        categoriesQuantitySizeText = "Quantity: ${categoryQuickSummary.subCategories.size}",
                    )
                }
                categoryQuickSummary.subCategories.forEachIndexed { subCategoryIndex, subCategory ->
                    SingleCategory(
                        index = subCategoryIndex + 1,
                        removeCategoryState = removeCategoryState,
                        categoryScreenActions = categoryScreenActions,
                        categoryQuickSummary = subCategory,
                    )
                }
            }
            Row(
                modifier = defaultModifier.padding(top = 0.dp, bottom = 0.dp),
                horizontalArrangement = Arrangement.End,
            ) {
                Button(
                    modifier = defaultModifier.weight(1f),
                    onClick = {
                        // TODO: go to form screen xd
                        showLongText(context, "Added subcategory!")
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.secondary),
                ) {
                    // TODO: translate me
                    Text(text = "Add subcategory")
                }
            }
        }
    }
    Divider()
}

@ExperimentalMaterialApi
@Composable
fun SingleCategoryDetails(
    categoryScreenActions: CategoryScreenActions,
    categoryQuickSummary: AbstractCategoryQuickSummary,
    removeCategoryState: RemoveCategoryState,
) {

    if (categoryQuickSummary is MainCategoryQuickSummary && categoryQuickSummary.numberOfExpensesWithoutSubCategories != 0L && categoryQuickSummary.subCategories.isNotEmpty()) {
        Row(
            modifier = defaultModifier.padding(top = 0.dp, bottom = 0.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Row(horizontalArrangement = Arrangement.Start) {
                Icon(
                    Icons.Filled.Article,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                )
                Text(text = stringResource(R.string.categoryScreen_singleCategoryQuickInfoNumberOfExpenses) + " ${categoryQuickSummary.numberOfExpensesWithoutSubCategories}")
            }

            Row(horizontalArrangement = Arrangement.End) {
                CategoryActionsStateless(
                    categoryScreenActions = categoryScreenActions,
                    categoryQuickSummary = categoryQuickSummary,
                    removeCategoryState = removeCategoryState,
                )
            }
        }
    } else {
        Row(
            modifier = defaultModifier.padding(top = 0.dp, bottom = 0.dp),
            horizontalArrangement = Arrangement.End,
        ) {
            CategoryActionsStateless(
                categoryScreenActions = categoryScreenActions,
                categoryQuickSummary = categoryQuickSummary,
                removeCategoryState = removeCategoryState,
            )
        }
    }

}

@Composable
fun CategoryActionsStateless(
    categoryScreenActions: CategoryScreenActions,
    categoryQuickSummary: AbstractCategoryQuickSummary,
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
