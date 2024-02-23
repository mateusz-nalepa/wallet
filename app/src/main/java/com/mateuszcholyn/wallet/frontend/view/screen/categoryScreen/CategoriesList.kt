package com.mateuszcholyn.wallet.frontend.view.screen.categoryScreen

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
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
    removeCategoryState: RemoveCategoryState,
) {
    LazyColumn(
        modifier =
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp),
    ) {
        itemsIndexed(categorySuccessContent.categoriesList) {index,  categoryQuickSummary ->
            SingleCategory(
                index = index + 1,
                removeCategoryState = removeCategoryState,
                categoryScreenActions = categoryScreenActions,
                categoryQuickSummary = categoryQuickSummary,
            )
        }
    }
}
