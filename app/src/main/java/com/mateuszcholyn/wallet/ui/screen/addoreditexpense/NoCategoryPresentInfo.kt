package com.mateuszcholyn.wallet.ui.screen.addoreditexpense

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
import androidx.navigation.NavHostController
import com.mateuszcholyn.wallet.R
import com.mateuszcholyn.wallet.ui.skeleton.NavDrawerItem
import com.mateuszcholyn.wallet.ui.util.defaultButtonModifier
import com.mateuszcholyn.wallet.ui.util.defaultModifier

@Composable
fun NoCategoryPresentInfo(
    navController: NavHostController,
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
            onClick = { navController.navigate(NavDrawerItem.Category.route) },
            modifier = defaultButtonModifier,
        ) {
            Text(text = stringResource(R.string.addFirstCategory))
        }
    }
}
