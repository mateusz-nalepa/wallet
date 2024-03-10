package com.mateuszcholyn.wallet.frontend.view.util

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mateuszcholyn.wallet.backend.api.categoriesquicksummary.AbstractCategoryQuickSummary
import com.mateuszcholyn.wallet.backend.api.categoriesquicksummary.SubCategoryQuickSummary

val defaultModifier =
    Modifier
        .padding(4.dp)
        .fillMaxWidth()

val buttonPadding = 4.dp

val defaultButtonModifier =
    Modifier
        .padding(buttonPadding)
        .height(60.dp)
        .fillMaxWidth()


val BOTTOM_BAR_HEIGHT = 56.dp

val subCategoryStartPadding = 16.dp

fun Modifier.subCategoryPaddingModifier(
    categoryQuickSummary: AbstractCategoryQuickSummary,
): Modifier =
    this.padding(
        start =
        if (categoryQuickSummary is SubCategoryQuickSummary) {
            subCategoryStartPadding
        } else {
            0.dp
        }
    )