package com.mateuszcholyn.wallet.frontend.view.screen.summary

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.mateuszcholyn.wallet.R
import com.mateuszcholyn.wallet.backend.api.core.expense.ExpenseId
import com.mateuszcholyn.wallet.backend.api.searchservice.SearchSingleResult
import com.mateuszcholyn.wallet.frontend.domain.usecase.core.expense.RemoveExpenseUseCase
import com.mateuszcholyn.wallet.frontend.view.composables.YesOrNoDialog
import com.mateuszcholyn.wallet.frontend.view.skeleton.NavDrawerItem
import com.mateuszcholyn.wallet.frontend.view.skeleton.routeWithId
import com.mateuszcholyn.wallet.frontend.view.util.asPrintableAmount
import com.mateuszcholyn.wallet.frontend.view.util.defaultModifier
import com.mateuszcholyn.wallet.util.localDateTimeUtils.toHumanText
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ShowExpenseModel @Inject constructor(
    private val removeExpenseUseCase: RemoveExpenseUseCase,
) : ViewModel() {


    fun removeExpenseById(expenseId: ExpenseId) {
        removeExpenseUseCase.invoke(expenseId)
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ShowExpense(
    id: Int,
    searchSingleResult: SearchSingleResult,
    navController: NavHostController,
    refreshFunction: () -> Unit,
    initialDetailsAreVisible: Boolean = false,
    showExpenseModel: ShowExpenseModel = hiltViewModel()
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
                    Text(text = searchSingleResult.paidAt.toHumanText())
                }

                Row(horizontalArrangement = Arrangement.End) {
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
                    val openDialog = remember { mutableStateOf(false) }
                    YesOrNoDialog(
                        openDialog = openDialog,
                        onConfirm = {
                            showExpenseModel.removeExpenseById(expenseId = searchSingleResult.expenseId)
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
