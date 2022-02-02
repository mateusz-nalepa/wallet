package com.mateuszcholyn.wallet.scaffold.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mateuszcholyn.wallet.domain.category.Category
import com.mateuszcholyn.wallet.domain.category.CategoryDetails
import com.mateuszcholyn.wallet.domain.category.CategoryService
import com.mateuszcholyn.wallet.scaffold.screens.fragments.SingleCategory
import com.mateuszcholyn.wallet.scaffold.util.ValidatedTextField
import com.mateuszcholyn.wallet.scaffold.util.categoryIsInvalid
import com.mateuszcholyn.wallet.scaffold.util.defaultButtonModifier
import com.mateuszcholyn.wallet.scaffold.util.defaultModifier
import org.kodein.di.compose.rememberInstance

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NewCategoryScreen() {
    val categoryService: CategoryService by rememberInstance()

    var categoryNameText by remember { mutableStateOf("") }

    var categoryListOptions by remember { mutableStateOf(listOf<CategoryDetails>()) }
    var categoryNamesOnly by remember { mutableStateOf(listOf<String>()) }

    fun refreshCategoryList() {
        categoryListOptions = categoryService.getAllWithDetailsOrderByUsageDesc()
        categoryNamesOnly = categoryListOptions.map { it.name }
    }

    refreshCategoryList()

    Column {

        Column(modifier = defaultModifier) {
            ValidatedTextField(
                    textFieldLabel = "Nazwa nowej kategorii",
                    value = categoryNameText,
                    onValueChange = { categoryNameText = it },
                    isValueInValidFunction = {
                        categoryIsInvalid(it, categoryNamesOnly)
                    },
                    valueInvalidText = "Nieprawidłowa wartość",
                    modifier = defaultModifier.testTag("NewCategoryTextField"),
            )
            Button(
                    onClick = {
                        categoryService.add(Category(name = categoryNameText))
                        categoryNameText = ""
                        refreshCategoryList()
                    },
                    modifier = defaultButtonModifier.testTag("AddNewCategoryButton"),
            ) {
                Text("Dodaj kategorię")
            }
        }


        Row(modifier = defaultModifier, horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = "Kategorie", modifier = defaultModifier.weight(1f))
            Text(text = "Ilość: ${categoryListOptions.size}", modifier = defaultModifier.weight(1f))
        }
        Row(modifier = defaultModifier) {
            Divider()
        }
        LazyColumn(
                modifier =
                Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 4.dp),

                ) {
            items(categoryListOptions) { categoryDetails ->
                SingleCategory(
                        categoryDetails = categoryDetails,
                        refreshCategoryListFunction = { refreshCategoryList() },
                        categoryNamesOnly = categoryNamesOnly,
                )
            }

        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Preview(showBackground = true)
@Composable
fun NewCategoryScreenPreview() {
    NewCategoryScreen()
}

