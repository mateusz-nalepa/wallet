package com.mateuszcholyn.wallet.frontend.view.screen.backup.backupV1.export

import com.mateuszcholyn.wallet.frontend.view.screen.backup.backupV1.BackupWalletV1
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.test.runTest
import org.junit.Test
import java.math.BigDecimal

class BackupV1JsonCreatorTest {

    @Test
    fun `should create backup v1`() = runTest {
        // given
        val givenRandomBackup =
            BackupWalletV1(
                categories = listOf(
                    BackupWalletV1.BackupCategoryV1(
                        id = "categoryId",
                        name = "categoryName",
                        expenses = listOf(
                            BackupWalletV1.BackupCategoryV1.BackupExpenseV1(
                                expenseId = "expenseId",
                                amount = BigDecimal("50"),
                                description = "desc",
                                paidAt = 7,
                            )
                        )
                    )
                )
            )

        // when
        val backupJson = BackupV1JsonCreator.createBackupWalletV1AsString(givenRandomBackup)

        // then
        backupJson shouldBe """{
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
    }


}