package com.mateuszcholyn.wallet.frontend.view.screen.settings.themedropdown

import com.mateuszcholyn.wallet.userConfig.language.WalletLanguage
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.collections.shouldNotContain
import io.kotest.matchers.shouldBe
import org.junit.Test

class LanguageDropdownElementsTest {

    @Test
    fun shouldNotReturnHodorLanguage() {
        // given
        val isHodorLanguageNotAvailable = true

        // when
        val languages = languageDropdownElements(isHodorLanguageNotAvailable)
        // then
        languages.size shouldBe 3
        languages shouldNotContain WalletLanguage.HODOR.toLanguageDropdownElement()
    }

    @Test
    fun shouldReturnHodorLanguage() {
        // given
        val isHodorLanguageNotAvailable = false

        // when
        val languages = languageDropdownElements(isHodorLanguageNotAvailable)
        // then
        languages.size shouldBe 4
        languages shouldContain WalletLanguage.HODOR.toLanguageDropdownElement()
    }
}