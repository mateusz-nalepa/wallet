package com.mateuszcholyn.wallet.frontend.view.screen.category

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.ListItem
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.mateuszcholyn.wallet.R
import com.mateuszcholyn.wallet.backend.api.categoriesquicksummary.CategoryQuickSummary
import com.mateuszcholyn.wallet.backend.api.core.category.CategoryId
import com.mateuszcholyn.wallet.frontend.view.composables.YesOrNoDialog
import com.mateuszcholyn.wallet.frontend.view.util.currentAppContext
import com.mateuszcholyn.wallet.frontend.view.util.defaultModifier
import com.mateuszcholyn.wallet.frontend.view.util.showShortText


@ExperimentalMaterialApi
@Composable
fun SingleCategory(
    onDeleteFunction: (CategoryId) -> Unit,
    onUpdateCategoryFunction: (CategoryQuickSummary) -> Unit,
    categoryQuickSummary: CategoryQuickSummary,
    initialDetailsAreVisible: Boolean = false,
    initialEditCategoryNameIsVisible: Boolean = false,
    categorySuccessContent: CategorySuccessContent,
) {
    val currentContext = currentAppContext()
    var detailsAreVisible by remember { mutableStateOf(initialDetailsAreVisible) }
    var editCategoryNameIsVisible by remember { mutableStateOf(initialEditCategoryNameIsVisible) }

    ListItem(
        modifier =
        Modifier
            .testTag("CategoryItem#${categoryQuickSummary.categoryId.id}")
            .clickable {
                detailsAreVisible = !detailsAreVisible
            },

        text = { Text(categoryQuickSummary.categoryName) },
        secondaryText = { Text(stringResource(R.string.amountOfExpenses) + " ${categoryQuickSummary.numberOfExpenses}") },
        trailing = {
            if (detailsAreVisible) {
                Icon(
                    Icons.Filled.ExpandLess,
                    contentDescription = null,
                    modifier = Modifier.size(32.dp),
                )
            } else {
                Icon(
                    Icons.Filled.ExpandMore,
                    contentDescription = null,
                    modifier = Modifier.size(32.dp),
                )
            }
        }
    )

    if (detailsAreVisible) {
        Row(
            modifier = defaultModifier.padding(top = 0.dp),
            horizontalArrangement = Arrangement.End
        ) {
            IconButton(
                onClick = {
                    editCategoryNameIsVisible = !editCategoryNameIsVisible
                }
            ) {
                Icon(
                    Icons.Filled.Edit,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                )
            }
            val openDialog = remember { mutableStateOf(false) }
            val categoryRemovedText = stringResource(R.string.categoryRemoved)
            val cannotRemoveCategoryWithExpensesText =
                stringResource(R.string.cannotRemoveCategoryWithExpenses)
            YesOrNoDialog(
                message = stringResource(R.string.reallyHardRemoveCategory),
                openDialog = openDialog,
                onConfirm = {
                    if (categoryQuickSummary.numberOfExpenses == 0L) {
                        onDeleteFunction.invoke(categoryQuickSummary.categoryId)
                        showShortText(
                            currentContext,
                            categoryRemovedText + " ${categoryQuickSummary.categoryName}"
                        )
                    } else {
                        showShortText(currentContext, cannotRemoveCategoryWithExpensesText)
                    }
                }
            )
            IconButton(
                onClick = { openDialog.value = true }
            ) {
                Icon(
                    Icons.Filled.DeleteForever,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                )
            }
        }
        if (editCategoryNameIsVisible) {
            EditCategoryForm(
                actualCategoryName = categoryQuickSummary.categoryName,
                onFormSubmit = { newCategoryName ->
                    onUpdateCategoryFunction.invoke(categoryQuickSummary.copy(categoryName = newCategoryName))
                    editCategoryNameIsVisible = false
                    detailsAreVisible = false
                },
                categorySuccessContent = categorySuccessContent,
            )
        }
    }

    Divider()

}
