package com.mateuszcholyn.wallet.frontend.view.screen.addoreditexpense

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.mateuszcholyn.wallet.R
import com.mateuszcholyn.wallet.frontend.view.util.defaultButtonModifier
import com.mateuszcholyn.wallet.frontend.view.util.defaultModifier

@Composable
fun NoCategoryPresentInfo(
    onMissingCategoriesNavigate: () -> Unit,
) {
    Column(
        modifier = defaultModifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(stringResource(R.string.categoryRequiredToAddExpense))
        Button(
            onClick = { onMissingCategoriesNavigate() },
            modifier = defaultButtonModifier,
        ) {
            Text(text = stringResource(R.string.addFirstCategory))
        }
    }
}
