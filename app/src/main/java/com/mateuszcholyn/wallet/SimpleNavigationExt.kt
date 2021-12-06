package com.mateuszcholyn.wallet

import android.view.MenuItem
import androidx.fragment.app.FragmentTransaction
import com.mateuszcholyn.wallet.domain.expense.Expense
import com.mateuszcholyn.wallet.ui.addoreditexpense.AddOrEditExpenseFragment
import com.mateuszcholyn.wallet.ui.category.CategoryFragment
import com.mateuszcholyn.wallet.ui.chat.ChatFragment
import com.mateuszcholyn.wallet.ui.message.SummaryFragment
import com.mateuszcholyn.wallet.view.showShortText

fun MainActivity.handleNavigation(item: MenuItem) {
    when (item.itemId) {
        R.id.nav_add_or_edit_expense -> switchToAddOrEditExpense()
        R.id.nav_message -> switchToSummaryFragment()
        R.id.nav_category -> switchToCategoryFragment()
        R.id.nav_chat -> switchToChatFragment()
        R.id.nav_share -> showShortText("Share")
        R.id.nav_send -> showShortText("Send")
    }
}


fun MainActivity.switchToAddOrEditExpense(
    withBackStack: Boolean = false,
    expense: Expense? = null
) {
    title = "Add Or Edit Expense"

    supportFragmentManager
        .beginTransaction()
        .replace(
            R.id.fragment_container,
            AddOrEditExpenseFragment(
                onExpenseAddedAction = { switchToSummaryFragment() },
                expenseToBeEdited = expense
            )
        )
        .withBackStack(withBackStack)
        .commit()
}

fun FragmentTransaction.withBackStack(withBackStack: Boolean): FragmentTransaction =
    when (withBackStack) {
        true -> this.addToBackStack("XDDD")
        false -> this
    }

fun MainActivity.switchToSummaryFragment() {
    title = "Summary Fragment"

    supportFragmentManager
        .beginTransaction()
        .replace(R.id.fragment_container, SummaryFragment(
            showAddExpenseFragmentFunction = {
                switchToAddOrEditExpense(withBackStack = true, null)
            },
            showEditExpenseFragmentFunction = { actualExpense ->
                switchToAddOrEditExpense(withBackStack = true, expense = actualExpense)
            }
        ))
        .commit()
}

fun MainActivity.switchToCategoryFragment() {
    title = "Category Fragment"

    supportFragmentManager
        .beginTransaction()
        .replace(R.id.fragment_container, CategoryFragment())
        .commit()
}


fun MainActivity.switchToChatFragment() {
    title = "Chat Fragment"

    supportFragmentManager
        .beginTransaction()
        .replace(R.id.fragment_container, ChatFragment())
        .commit()
}