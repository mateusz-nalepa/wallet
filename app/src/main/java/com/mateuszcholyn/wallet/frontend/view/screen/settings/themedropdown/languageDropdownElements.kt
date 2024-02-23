package com.mateuszcholyn.wallet.frontend.view.screen.settings.themedropdown

import com.mateuszcholyn.wallet.frontend.view.dropdown.DropdownElement
import com.mateuszcholyn.wallet.userConfig.language.WalletLanguage

data class LanguageDropdownElement(
    override val name: String? = null,
    override val nameKey: Int,
    val walletLanguage: WalletLanguage,
) : DropdownElement

fun languageDropdownElements(): List<LanguageDropdownElement> =
    WalletLanguage
        .entries
        .toList()
        .map { it.toLanguageDropdownElement() }

fun WalletLanguage.toLanguageDropdownElement(): LanguageDropdownElement =
    LanguageDropdownElement(
        nameKey = nameKey,
        walletLanguage = this,
    )
