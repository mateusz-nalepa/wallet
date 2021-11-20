package com.mateuszcholyn.wallet

import com.mateuszcholyn.wallet.ui.chat.ChatFragment
import com.mateuszcholyn.wallet.ui.message.SummaryFragment

fun SimpleNavigation.switchToMessageFragment() {
    title = "Summary Fragment"

    supportFragmentManager
        .beginTransaction()
        .replace(R.id.fragment_container, SummaryFragment())
        .commit()
}

fun SimpleNavigation.switchToChatFragment() {
    title = "Chat Fragment"

    supportFragmentManager
        .beginTransaction()
        .replace(R.id.fragment_container, ChatFragment())
        .commit()
}