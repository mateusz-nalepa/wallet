package com.mateuszcholyn.wallet.scaffold.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mateuszcholyn.wallet.domain.category.Category
import com.mateuszcholyn.wallet.domain.category.CategoryService
import com.mateuszcholyn.wallet.scaffold.util.defaultButtonModifier
import com.mateuszcholyn.wallet.scaffold.util.defaultModifier
import com.mateuszcholyn.wallet.view.showShortText
import org.kodein.di.compose.rememberInstance

@ExperimentalMaterialApi
@Composable
fun NewCategoryScreen() {
    val categoryService: CategoryService by rememberInstance()

    var categoryNameText by remember { mutableStateOf("") }

    var categoryListOptions by remember { mutableStateOf(listOf<Category>()) }

    fun refreshCategoryList() {
        categoryListOptions = categoryService.getAllOrderByUsageDesc()
    }

    refreshCategoryList()

    Column {
        Row(
                modifier = defaultModifier,
        ) {

            OutlinedTextField(
                    value = categoryNameText,
                    onValueChange = { categoryNameText = it },
                    label = { Text("Nazwa nowej kategorii") },
                    modifier = defaultModifier,
                    singleLine = true,
            )
        }
        Row(modifier = defaultModifier) {
            Button(
                    onClick = {
                        categoryService.add(Category(name = categoryNameText))
                        categoryNameText = ""
                        refreshCategoryList()
                    },
                    modifier = defaultButtonModifier,
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
            items(categoryListOptions) { categoryModel ->
                ListItem(
                        text = { Text(categoryModel.name) },
                        secondaryText = { Text("Ilość wydatków: TBD") },
                        trailing = {
                            IconButton(
                                    onClick = {
                                        showShortText("NOT IMPLEMENTED YET")
                                    }
                            ) {
                                Icon(
                                        Icons.Filled.Delete,
                                        contentDescription = null,
                                        modifier = Modifier.size(32.dp),

                                        )
                            }

                        }
                )
                Divider()
            }

        }
    }
}

@ExperimentalMaterialApi
@Preview(showBackground = true)
@Composable
fun NewCategoryScreenPreview() {
    NewCategoryScreen()
}

