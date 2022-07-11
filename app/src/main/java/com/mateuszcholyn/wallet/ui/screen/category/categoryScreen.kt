package com.mateuszcholyn.wallet.ui.screen.category

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun NewCategoryScreen() {
    Column {
        NewCategoryForm()
        NumberOfCategories()
        Divider()
        CategoriesList()
    }
}

@Preview(showBackground = true)
@Composable
fun NewCategoryScreenPreview() {
    NewCategoryScreen()
}

