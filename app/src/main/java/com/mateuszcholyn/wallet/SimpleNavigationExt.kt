package com.mateuszcholyn.wallet

import com.mateuszcholyn.wallet.ui.chat.ChatFragment
import com.mateuszcholyn.wallet.ui.message.MessageFragment

fun SimpleNavigation.switchToMessageFragment() {
    title = "Message Fragment"

    supportFragmentManager
        .beginTransaction()
        .replace(R.id.fragment_container, MessageFragment())
        .commit()
}

fun SimpleNavigation.switchToChatFragment() {
    title = "Chat Fragment"

    supportFragmentManager
        .beginTransaction()
        .replace(R.id.fragment_container, ChatFragment())
        .commit()
}