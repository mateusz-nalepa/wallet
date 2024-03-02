package com.mateuszcholyn.wallet.frontend.view.screen.expenseform

import io.kotest.matchers.shouldBe
import org.junit.Test

class AmountValidator {

    @Test
    fun isValidAmount() {
        "5".isAmountValid() shouldBe true
        "5,".isAmountValid() shouldBe true
        "5.".isAmountValid() shouldBe true
        "5,00".isAmountValid() shouldBe true
        "5.00".isAmountValid() shouldBe true

        "5.123.12".isAmountValid() shouldBe false
        "5.12.12".isAmountValid() shouldBe false
        "5,12011".isAmountValid() shouldBe false
        "asd".isAmountValid() shouldBe false
        "34234.234.dd".isAmountValid() shouldBe false
    }

}