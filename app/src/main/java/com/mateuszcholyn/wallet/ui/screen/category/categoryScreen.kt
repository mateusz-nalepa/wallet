package com.mateuszcholyn.wallet.ui.screen.category

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import com.mateuszcholyn.wallet.R
import com.mateuszcholyn.wallet.domain.category.Category
import com.mateuszcholyn.wallet.domain.category.CategoryDetails
import com.mateuszcholyn.wallet.domain.category.CategoryService
import com.mateuszcholyn.wallet.ui.util.defaultModifier
import com.mateuszcholyn.wallet.util.EMPTY_STRING
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
        private val categoryService: CategoryService,
) : ViewModel() {

    fun categoryService(): CategoryService =
            categoryService

}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NewCategoryScreen(
        categoryViewModel: CategoryViewModel = hiltViewModel()
) {
    val categoryService = categoryViewModel.categoryService()

    var categoryListOptions by remember { mutableStateOf(listOf<CategoryDetails>()) }
    var categoryNamesOnly by remember { mutableStateOf(listOf<String>()) }

    fun refreshCategoryList() {
        categoryListOptions = categoryService.getAllWithDetailsOrderByUsageDesc()
        categoryNamesOnly = categoryListOptions.map { it.name }
    }

    refreshCategoryList()

    Column {
        CategoryForm(
                textFieldLabel = stringResource(R.string.newCategoryName),
                buttonLabel = stringResource(R.string.addCategory),
                initialCategoryName = EMPTY_STRING,
                categoryNamesOnly = categoryNamesOnly,
                onFormSubmit = { actualCategory ->
                    categoryService.add(Category(name = actualCategory))
                    refreshCategoryList()
                }
        )

        Row(modifier = defaultModifier, horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = stringResource(R.string.categories), modifier = defaultModifier.weight(1f))
            Text(text = stringResource(R.string.quantity) + " ${categoryListOptions.size}", modifier = defaultModifier.weight(1f))
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
                        categoryService = categoryService,
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

