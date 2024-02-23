package com.mateuszcholyn.wallet.frontend.view.screen.settings.themedropdown

import com.mateuszcholyn.wallet.frontend.view.dropdown.DropdownElement
import com.mateuszcholyn.wallet.userConfig.language.WalletLanguage

data class LanguageDropdownElement(
    override val name: String? = null,
    override val nameKey: Int,
    val walletLanguage: WalletLanguage,
) : DropdownElement

fun languageDropdownElements(isHodorLanguageNotAvailable: Boolean): List<LanguageDropdownElement> =
    WalletLanguage
        .entries
        .toList()
        .filter {
            !(isHodorLanguageNotAvailable && it == WalletLanguage.HODOR)
        }
        .map { it.toLanguageDropdownElement() }


fun WalletLanguage.toLanguageDropdownElement(): LanguageDropdownElement =
    LanguageDropdownElement(
        nameKey = nameKey,
        walletLanguage = this,
    )
