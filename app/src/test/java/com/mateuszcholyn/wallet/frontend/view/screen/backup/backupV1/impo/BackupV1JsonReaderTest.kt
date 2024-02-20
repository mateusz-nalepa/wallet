package com.mateuszcholyn.wallet.frontend.view.screen.backup.backupV1.impo

import com.mateuszcholyn.wallet.frontend.view.screen.backup.backupV1.BackupWalletV1
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.test.runTest
import org.junit.Test
import java.math.BigDecimal

class BackupV1JsonReaderTest {

    @Test
    fun `should read backup v1`() = runTest {
        // given
        val backupJson = """{
  "categories" : [ {
    "id" : "categoryId",
    "name" : "categoryName",
    "expenses" : [ {
      "expenseId" : "expenseId",
      "amount" : 50,
      "description" : "desc",
      "paidAt" : 7
    } ]
  } ],
  "version" : 1
}"""

        // when
        val backupWalletV1 = BackupV1JsonReader.readBackupWalletV1(backupJson)

        // then
        val expectedBackupWalletV1 =
            BackupWalletV1(
                categories = listOf(
                    BackupWalletV1.BackupCategoryV1(
                        id = "categoryId",
                        name = "categoryName",
                        expenses = listOf(
                            BackupWalletV1.BackupCategoryV1.BackupExpenseV1(
                                expenseId = "expenseId",
                                amount = BigDecimal("50"), description = "desc",
                                paidAt = 7,
                            )
                        )
                    )
                )
            )

        backupWalletV1 shouldBe expectedBackupWalletV1
    }

    @Test
    fun `should throw exception on version greater than 1`() = runTest {
        // given
        val backupJson = """{
  "version" : 2
}"""

        // when
        val throwable =
            shouldThrow<BackupWalletV1NotSupportedVersionException> {
                BackupV1JsonReader.readBackupWalletV1(backupJson)
            }

        // then
        throwable.message shouldBe "Not supported backup version. Expected 1. Got: 2"
    }


}