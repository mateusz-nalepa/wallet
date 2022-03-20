package com.mateuszcholyn.wallet.ui.screen.category

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.mateuszcholyn.wallet.R
import com.mateuszcholyn.wallet.domain.category.Category
import com.mateuszcholyn.wallet.domain.category.CategoryDetails
import com.mateuszcholyn.wallet.domain.category.CategoryService
import com.mateuszcholyn.wallet.ui.composables.YesOrNoDialog
import com.mateuszcholyn.wallet.ui.util.defaultModifier
import com.mateuszcholyn.wallet.ui.util.showShortText
import org.kodein.di.compose.rememberInstance


@ExperimentalMaterialApi
@Composable
fun SingleCategory(
        categoryDetails: CategoryDetails,
        refreshCategoryListFunction: () -> Unit,
        initialDetailsAreVisible: Boolean = false,
        initialEditCategoryNameIsVisible: Boolean = false,
        categoryNamesOnly: List<String> = emptyList(),
) {
    val categoryService: CategoryService by rememberInstance()
    var detailsAreVisible by remember { mutableStateOf(initialDetailsAreVisible) }

    var editCategoryNameIsVisible by remember { mutableStateOf(initialEditCategoryNameIsVisible) }

    ListItem(
            modifier =
            Modifier
                    .testTag("CategoryItem#${categoryDetails.id}")
                    .clickable {
                        detailsAreVisible = !detailsAreVisible
                    },

            text = { Text(categoryDetails.name) },
            secondaryText = { Text(stringResource(R.string.amountOfExpenses) +" ${categoryDetails.numberOfExpenses}") },
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
        val notImplementedText = stringResource(R.string.notImplemented)
        Row(modifier = defaultModifier.padding(top = 0.dp), horizontalArrangement = Arrangement.End) {
            IconButton(
                    onClick = {
                        showShortText(notImplementedText)
                    }
            ) {
                Icon(
                        Icons.Filled.PresentToAll,
                        contentDescription = null,
                        modifier = Modifier.size(24.dp),
                )
            }
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
            val cannotRemoveCategoryWithExpensesText = stringResource(R.string.cannotRemoveCategoryWithExpenses)
            YesOrNoDialog(
                    message = stringResource(R.string.reallyHardRemoveCategory),
                    openDialog = openDialog,
                    onConfirm = {
                        if (categoryDetails.numberOfExpenses == 0L) {
                            categoryService.remove(categoryDetails.id)
                            refreshCategoryListFunction()
                            showShortText( categoryRemovedText +" ${categoryDetails.name}")
                        } else {
                            showShortText(cannotRemoveCategoryWithExpensesText)
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
            CategoryForm(
                    textFieldLabel = stringResource(R.string.updatedCategoryName),
                    buttonLabel = stringResource(R.string.update),
                    initialCategoryName = categoryDetails.name,
                    categoryNamesOnly = categoryNamesOnly,
                    onFormSubmit = { actualCategory ->
                        categoryService.updateCategory(
                                categoryDetails.toCategory(actualCategory)
                        )
                        editCategoryNameIsVisible = false
                        detailsAreVisible = false
                        refreshCategoryListFunction()
                    }
            )
        }
    }



    Divider()

}


//@ExperimentalMaterialApi
//@Preview(showBackground = true)
//@Composable
//fun SingleCategoryPreview() {
//    withDI(di = previewDi()) {
//        MaterialTheme {
//            Column {
//                SingleCategory(
//                        categoryDetails =
//                        CategoryDetails(
//                                id = 1,
//                                name = "Zakupy",
//                                numberOfExpenses = 14,
//                        ),
//                        refreshCategoryListFunction = {},
//                        initialDetailsAreVisible = true,
//                        initialEditCategoryNameIsVisible = true,
//                )
//            }
//        }
//    }
//}


fun CategoryDetails.toCategory(newName: String): Category =
        Category(
                id = id,
                name = newName,
        )