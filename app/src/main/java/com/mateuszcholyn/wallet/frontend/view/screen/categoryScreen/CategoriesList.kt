package com.mateuszcholyn.wallet.frontend.view.screen.categoryScreen

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mateuszcholyn.wallet.frontend.view.screen.categoryScreen.singleCategoryDetails.SingleCategory

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CategoriesList(
    categorySuccessContent: CategorySuccessContent,
    categoryScreenActions: CategoryScreenActions,
) {
    LazyColumn(
        modifier =
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp),
    ) {
        items(categorySuccessContent.categoriesList) { categoryQuickSummary ->
            SingleCategory(
                categoryScreenActions = categoryScreenActions,
                categoryQuickSummary = categoryQuickSummary,
            )
        }
    }
}
