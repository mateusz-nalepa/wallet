package com.mateuszcholyn.wallet

import android.view.MenuItem
import androidx.fragment.app.FragmentTransaction
import com.mateuszcholyn.wallet.ui.addoreditexpense.AddOrEditExpenseFragment
import com.mateuszcholyn.wallet.ui.chat.ChatFragment
import com.mateuszcholyn.wallet.ui.message.SummaryFragment
import com.mateuszcholyn.wallet.view.showShortText

fun SimpleNavigation.handleNavigation(item: MenuItem) {
    when (item.itemId) {
        R.id.nav_add_or_edit_expense -> switchToAddOrEditExpense()
        R.id.nav_message -> switchToSummaryFragment()
        R.id.nav_chat -> switchToChatFragment()
        R.id.nav_share -> showShortText("Share")
        R.id.nav_send -> showShortText("Send")
    }
}


fun SimpleNavigation.switchToAddOrEditExpense(withBackStack: Boolean = false) {
    title = "Add Or Edit Expense"

    supportFragmentManager
        .beginTransaction()
        .replace(R.id.fragment_container, AddOrEditExpenseFragment {
            switchToSummaryFragment()
        })
        .withBackStack(withBackStack)
        .commit()
}

fun FragmentTransaction.withBackStack(withBackStack: Boolean): FragmentTransaction =
    when (withBackStack) {
        true -> this.addToBackStack("XDDD")
        false -> this
    }

fun SimpleNavigation.switchToSummaryFragment() {
    title = "Summary Fragment"

    supportFragmentManager
        .beginTransaction()
        .replace(R.id.fragment_container, SummaryFragment {
            switchToAddOrEditExpense(withBackStack = true)
        })
        .commit()
}

fun SimpleNavigation.switchToChatFragment() {
    title = "Chat Fragment"

    supportFragmentManager
        .beginTransaction()
        .replace(R.id.fragment_container, ChatFragment())
        .commit()
}