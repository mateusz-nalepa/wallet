package com.mateuszcholyn.wallet.frontend.view.screen.categoryScreen.singleCategoryDetails.actions.edit

import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mateuszcholyn.wallet.backend.api.categoriesquicksummary.AbstractCategoryQuickSummary
import com.mateuszcholyn.wallet.backend.api.categoriesquicksummary.MainCategoryQuickSummary
import com.mateuszcholyn.wallet.backend.api.categoriesquicksummary.SubCategoryQuickSummary
import com.mateuszcholyn.wallet.backend.api.core.category.SubCategoryId
import com.mateuszcholyn.wallet.frontend.view.screen.categoryScreen.CategoryScreenActions

@Composable
fun EditSingleCategoryIconButton(
    categoryQuickSummary: AbstractCategoryQuickSummary,
    categoryScreenActions: CategoryScreenActions,
) {
    IconButton(
        onClick = {
            when (categoryQuickSummary) {
                is MainCategoryQuickSummary -> {
                    categoryScreenActions.onUpdateCategoryAction.invoke(categoryQuickSummary.id)
                }

                is SubCategoryQuickSummary -> {
                    categoryScreenActions.onUpdateSubCategoryAction.invoke(
                        categoryQuickSummary.mainCategoryId,
                        SubCategoryId(categoryQuickSummary.id.id),
                    )
                }
            }
        }
    ) {
        Icon(
            Icons.Filled.Edit,
            contentDescription = null,
            modifier = Modifier.size(24.dp),
        )
    }
}

