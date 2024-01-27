package com.mateuszcholyn.wallet.app.usecase.backup.impo

import com.mateuszcholyn.wallet.app.setupintegrationtests.BaseIntegrationTest
import com.mateuszcholyn.wallet.backend.impl.infrastructure.sqlite.converters.InstantConverter
import com.mateuszcholyn.wallet.frontend.view.screen.backup.backupV1.BackupWalletV1
import com.mateuszcholyn.wallet.manager.CategoryScope
import com.mateuszcholyn.wallet.manager.category
import com.mateuszcholyn.wallet.manager.expense
import com.mateuszcholyn.wallet.manager.ext.backup.export.exportV1UseCase
import com.mateuszcholyn.wallet.manager.ext.backup.impo.importV1UseCase
import com.mateuszcholyn.wallet.manager.ext.core.expense.addExpenseUseCase
import com.mateuszcholyn.wallet.manager.getAllCoreCategories
import com.mateuszcholyn.wallet.manager.randomBackupCategoryV1
import com.mateuszcholyn.wallet.manager.randomBackupExpenseV1
import com.mateuszcholyn.wallet.manager.randomCategoryName
import com.mateuszcholyn.wallet.manager.validator.LocalDateTimeValidator.assertInstant
import com.mateuszcholyn.wallet.manager.validator.validate
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Test

@HiltAndroidTest
class BackupExportV1UseCaseTest : BaseIntegrationTest() {

    @Test
    fun shouldImportV1Data() {
        // given
        val givenRandomBackupCategoryV1 = randomBackupCategoryV1()
        val givenRandomBackupExpenseV1 =
            randomBackupExpenseV1(categoryId = givenRandomBackupCategoryV1.id)

        val givenBackupWalletV1 =
            BackupWalletV1(
                version = 1,
                categories = listOf(givenRandomBackupCategoryV1),
                expenses = listOf(givenRandomBackupExpenseV1),
            )

        val manager = initExpenseAppManager {}

        // when
        manager.importV1UseCase {
            backupWalletV1 = givenBackupWalletV1
        }

        // then
        manager.validate {
            numberOfCoreCategoriesEqualTo(1)
            numberOfCoreExpensesEqualTo(1)
        }
    }

    @Test
    fun `should remove all expenses and then import from backup`() {
        // given
        val numberOfExpensesInBackupData = 5
        val numberOfNewExpensesAfterBackup = 3
        lateinit var categoryScope: CategoryScope

        val manager =
            initExpenseAppManager {
                categoryScope = category {
                    repeat(numberOfExpensesInBackupData) {
                        expense {}
                    }
                }
            }

        // and backup is created
        val givenBackupWalletV1 = manager.exportV1UseCase()

        // new expenses are added
        repeat(numberOfNewExpensesAfterBackup) {
            manager.addExpenseUseCase {
                categoryId = categoryScope.categoryId
            }
        }

        // when import with parameter removeAllBeforeImport is set to true
        manager.importV1UseCase {
            backupWalletV1 = givenBackupWalletV1
            removeAllBeforeImport = true
        }

        // then
        manager.validate {
            numberOfCoreExpensesEqualTo(numberOfExpensesInBackupData)
        }
    }

    @Test
    fun `should add a new category when database does not have category with given name`() {
        // given
        val givenCategoryNameInDb = "existing category name"
        val manager =
            initExpenseAppManager {
                category {
                    categoryName = givenCategoryNameInDb
                }
            }

        val givenCategoryNameFromImportFile = "non existing category name"
        val givenRandomBackupCategoryV1 =
            randomBackupCategoryV1(name = givenCategoryNameFromImportFile)

        val givenBackupWalletV1 =
            BackupWalletV1(
                version = 1,
                categories = listOf(givenRandomBackupCategoryV1),
                expenses = emptyList(),
            )


        // when
        manager.importV1UseCase {
            backupWalletV1 = givenBackupWalletV1
        }

        // then
        manager.validate {
            numberOfCoreCategoriesEqualTo(2)
        }
        val firstCategory = manager.getAllCoreCategories().first()
        assert(firstCategory.name == givenCategoryNameInDb)

        val lastCategory = manager.getAllCoreCategories().last()
        assert(lastCategory.name == givenCategoryNameFromImportFile)
    }

    @Test
    fun `should not create a new category when database have category with given name`() {
        // given
        val givenCategoryName = randomCategoryName()

        val givenRandomBackupCategoryV1 =
            randomBackupCategoryV1(name = givenCategoryName)

        val givenBackupWalletV1 =
            BackupWalletV1(
                version = 1,
                categories = listOf(givenRandomBackupCategoryV1),
                expenses = emptyList(),
            )

        val manager =
            initExpenseAppManager {
                category {
                    categoryName = givenCategoryName
                }
            }

        // when
        manager.importV1UseCase {
            backupWalletV1 = givenBackupWalletV1
        }

        // then
        manager.validate {
            numberOfCoreCategoriesEqualTo(1)
        }
        assert(
            manager
                .getAllCoreCategories()
                .first()
                .name == givenCategoryName
        )
    }

    @Test
    fun importedDataContainsPaidAtInInstantFormat() {
        // given
        val givenRandomBackupCategoryV1 = randomBackupCategoryV1()
        val givenRandomBackupExpenseV1 =
            randomBackupExpenseV1(categoryId = givenRandomBackupCategoryV1.id)

        val givenBackupWalletV1 =
            BackupWalletV1(
                version = 1,
                categories = listOf(givenRandomBackupCategoryV1),
                expenses = listOf(givenRandomBackupExpenseV1),
            )

        val manager = initExpenseAppManager {}

        // when
        manager.importV1UseCase {
            backupWalletV1 = givenBackupWalletV1
        }

        // then
        val expenseFromDb =
            manager
                .expenseAppDependencies
                .expenseRepositoryV2
                .getAllExpenses()
                .first()

        assertInstant(
            expenseFromDb.paidAt,
            InstantConverter.toInstant(givenBackupWalletV1.expenses.first().paidAt),
        ) {
            """Expected date as: ${InstantConverter.toInstant(givenBackupWalletV1.expenses.first().paidAt)}.
                Got: ${expenseFromDb.paidAt}
                """
        }
    }

}
