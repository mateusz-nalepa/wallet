package com.mateuszcholyn.wallet.scaffold.screens.fragments

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mateuszcholyn.wallet.domain.category.Category
import com.mateuszcholyn.wallet.domain.category.CategoryDetails
import com.mateuszcholyn.wallet.domain.category.CategoryService
import com.mateuszcholyn.wallet.scaffold.util.YesOrNoDialog
import com.mateuszcholyn.wallet.scaffold.util.defaultModifier
import com.mateuszcholyn.wallet.util.previewDi
import com.mateuszcholyn.wallet.view.showShortText
import org.kodein.di.compose.rememberInstance
import org.kodein.di.compose.withDI

@ExperimentalMaterialApi
@Composable
fun SingleCategory(
        categoryDetails: CategoryDetails,
        refreshCategoryListFunction: () -> Unit,
        initialDetailsAreVisible: Boolean = false,
        initialEditCategoryNameIsVisible: Boolean = false,
) {
    val categoryService: CategoryService by rememberInstance()
    var detailsAreVisible by remember { mutableStateOf(initialDetailsAreVisible) }
    var editedCategoryNameText by remember { mutableStateOf(categoryDetails.name) }
    var isCategoryNameInvalid by remember { mutableStateOf(false) }

    var editCategoryNameIsVisible by remember { mutableStateOf(initialEditCategoryNameIsVisible) }

    ListItem(
            modifier =
            Modifier
                    .testTag("CategoryItem#${categoryDetails.id}")
                    .clickable {
                        detailsAreVisible = !detailsAreVisible
                    },

            text = { Text(categoryDetails.name) },
            secondaryText = { Text("Ilość wydatków: ${categoryDetails.numberOfExpenses}") },
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

        Row(modifier = defaultModifier.padding(top = 0.dp), horizontalArrangement = Arrangement.End) {
            IconButton(
                    onClick = {
                        showShortText("NOT IMPLEMENTED!")
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
            YesOrNoDialog(
                    message = "Na pewno usunąć kategorię?",
                    openDialog = openDialog,
                    onConfirm = {
                        if (categoryDetails.numberOfExpenses == 0L) {
                            categoryService.remove(categoryDetails.id)
                            refreshCategoryListFunction()
                            showShortText("Usunięto kategorię: ${categoryDetails.name}")
                        } else {
                            showShortText("Nie możesz tego zrobić")
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
            Column {
                OutlinedTextField(
                        value = editedCategoryNameText,
                        onValueChange = {
                            isCategoryNameInvalid = it.isCategoryNameInvalid()
                            editedCategoryNameText = it
                        },
                        label = { Text("Kategoria") },
                        modifier = defaultModifier,
                        singleLine = true,
                        isError = isCategoryNameInvalid,
                        trailingIcon = {
                            if (isCategoryNameInvalid) {
                                Icon(Icons.Filled.Error, "error")
                            } else {
                                IconButton(
                                        onClick = {
                                            categoryService.updateCategory(
                                                    categoryDetails.toCategory(editedCategoryNameText)
                                            )
                                            editCategoryNameIsVisible = false
                                            detailsAreVisible = false
                                            refreshCategoryListFunction()
                                        }
                                ) {
                                    Icon(Icons.Filled.Done, "update")
                                }
                            }
                        },
                )
                if (isCategoryNameInvalid) {
                    Text(
                            text = "Niepoprawna wartość",
                            color = MaterialTheme.colors.error,
                            style = MaterialTheme.typography.caption,
                            modifier = Modifier.padding(start = 16.dp)
                    )
                }

            }
        }
    }



    Divider()

}

fun String.isCategoryNameInvalid(): Boolean =
        this.isBlank()

@ExperimentalMaterialApi
@Preview(showBackground = true)
@Composable
fun SingleCategoryPreview() {
    withDI(di = previewDi()) {
        MaterialTheme {
            Column {
                SingleCategory(
                        categoryDetails =
                        CategoryDetails(
                                id = 1,
                                name = "Zakupy",
                                numberOfExpenses = 14,
                        ),
                        refreshCategoryListFunction = {},
                        initialDetailsAreVisible = true,
                        initialEditCategoryNameIsVisible = true,
                )
            }
        }
    }
}


fun CategoryDetails.toCategory(newName: String): Category =
        Category(
                id = id,
                name = newName,
        )