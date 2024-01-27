package com.mateuszcholyn.wallet.app.usecase.backup.export

import com.mateuszcholyn.wallet.app.setupintegrationtests.BaseIntegrationTest
import com.mateuszcholyn.wallet.backend.impl.infrastructure.sqlite.converters.InstantConverter
import com.mateuszcholyn.wallet.manager.CategoryScope
import com.mateuszcholyn.wallet.manager.ExpenseScope
import com.mateuszcholyn.wallet.manager.category
import com.mateuszcholyn.wallet.manager.expense
import com.mateuszcholyn.wallet.manager.ext.backup.export.exportV1UseCase
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Test

@HiltAndroidTest
class BackupExportV1UseCaseTest : BaseIntegrationTest() {

    @Test
    fun shouldExportV1Data() {
        // given
        // TODO: validate exported data? XDD
        lateinit var firstCategory: CategoryScope
        lateinit var firstExpense: ExpenseScope

        lateinit var secondCategory: CategoryScope
        lateinit var secondExpense: ExpenseScope
        val manager =
            initExpenseAppManager {
                firstCategory = category {
                    firstExpense = expense {}
                }
                secondCategory = category {
                    secondExpense = expense {}
                }
            }

        // when
        val backupWalletV1 = manager.exportV1UseCase()

        // then
        backupWalletV1.validate {
            versionIsEqualTo1()
            numberOfCategoriesEqualTo(2)
            numberOfExpensesEqualTo(2)
        }
    }


    @Test
    fun exportedDataContainsPaidAtInLongFormat() {
        // given
        lateinit var firstExpense: ExpenseScope

        val manager =
            initExpenseAppManager {
                category {
                    firstExpense = expense {}
                }
            }

        // when
        val backupWalletV1 = manager.exportV1UseCase()

        // then
        assert(
            backupWalletV1.expenses.first().paidAt == InstantConverter.toLong(firstExpense.paidAt),
        ) {
            """Expected date as: ${InstantConverter.toLong(firstExpense.paidAt)}.
                Got: ${backupWalletV1.expenses.first().paidAt}
                """
        }
    }
}
