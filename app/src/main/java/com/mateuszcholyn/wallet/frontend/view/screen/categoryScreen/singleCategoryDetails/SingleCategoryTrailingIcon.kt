package com.mateuszcholyn.wallet.frontend.view.screen.categoryScreen.singleCategoryDetails

import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SingleCategoryTrailingIcon(
    detailsAreVisible: Boolean,
) {
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