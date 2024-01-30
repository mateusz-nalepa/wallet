package com.mateuszcholyn.wallet.frontend.view.screen.summary

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.ListItem
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CopyAll
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Paid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.mateuszcholyn.wallet.R
import com.mateuszcholyn.wallet.backend.api.searchservice.SearchSingleResult
import com.mateuszcholyn.wallet.frontend.view.composables.YesOrNoDialog
import com.mateuszcholyn.wallet.frontend.view.skeleton.NavDrawerItem
import com.mateuszcholyn.wallet.frontend.view.skeleton.copyExpense
import com.mateuszcholyn.wallet.frontend.view.skeleton.routeWithId
import com.mateuszcholyn.wallet.frontend.view.util.asPrintableAmount
import com.mateuszcholyn.wallet.frontend.view.util.defaultModifier
import com.mateuszcholyn.wallet.util.localDateTimeUtils.fromUTCInstantToUserLocalTimeZone
import com.mateuszcholyn.wallet.util.localDateTimeUtils.toHumanDateTimeText


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ShowExpense(
    id: Int,
    searchSingleResult: SearchSingleResult,
    navController: NavHostController,
    refreshFunction: () -> Unit,
    initialDetailsAreVisible: Boolean = false,
    showSingleExpenseViewModel: ShowSingleExpenseViewModel = hiltViewModel()
) {

    var detailsAreVisible by remember { mutableStateOf(initialDetailsAreVisible) }
    ListItem(
        text = { Text("${id + 1}. ${searchSingleResult.categoryName}") },
        trailing = {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(text = searchSingleResult.amount.asPrintableAmount(), fontSize = 16.sp)
                Icon(
                    Icons.Filled.Paid,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                )
            }
        },
        modifier = defaultModifier.clickable {
            detailsAreVisible = !detailsAreVisible
        },
    )

    if (detailsAreVisible) {
        Column {
            Row(modifier = defaultModifier.padding(bottom = 0.dp)) {
                Icon(
                    Icons.Filled.Description,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                )
                Text(text = searchSingleResult.descriptionOrDefault(stringResource(R.string.noDescription)))
            }
            Row(
                modifier = defaultModifier.padding(bottom = 0.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {

                Row(horizontalArrangement = Arrangement.Start) {
                    Icon(
                        Icons.Filled.Event,
                        contentDescription = null,
                        modifier = Modifier.size(24.dp),
                    )
                    Text(
                        text = searchSingleResult.paidAt.fromUTCInstantToUserLocalTimeZone()
                            .toHumanDateTimeText()
                    )
                }

                Row(horizontalArrangement = Arrangement.End) {
                    // copy
                    IconButton(
                        onClick = {
                            navController.navigate(
                                NavDrawerItem.AddOrEditExpense.copyExpense(
                                    expenseId = searchSingleResult.expenseId.id
                                )
                            )
                        }
                    ) {
                        Icon(
                            Icons.Filled.CopyAll,
                            contentDescription = null,
                            modifier = Modifier.size(24.dp),
                        )
                    }
                    // edit
                    IconButton(
                        onClick = {
                            navController.navigate(
                                NavDrawerItem.AddOrEditExpense.routeWithId(
                                    expenseId = searchSingleResult.expenseId.id
                                )
                            )
                        }
                    ) {
                        Icon(
                            Icons.Filled.Edit,
                            contentDescription = null,
                            modifier = Modifier.size(24.dp),
                        )
                    }
                    // remove
                    val openDialog = remember { mutableStateOf(false) }
                    YesOrNoDialog(
                        openDialog = openDialog,
                        onConfirm = {
                            showSingleExpenseViewModel.removeExpenseById(expenseId = searchSingleResult.expenseId)
                            refreshFunction()
                            detailsAreVisible = false

                        }
                    )
                    IconButton(
                        onClick = {
                            openDialog.value = true
                        }
                    ) {
                        Icon(
                            Icons.Filled.DeleteForever,
                            contentDescription = null,
                            modifier = Modifier.size(24.dp),
                        )
                    }
                }


            }
        }
    }
    Divider()
}
