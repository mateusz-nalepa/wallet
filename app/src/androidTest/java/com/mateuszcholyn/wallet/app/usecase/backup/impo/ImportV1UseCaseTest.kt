package com.mateuszcholyn.wallet.app.usecase.backup.impo

import com.mateuszcholyn.wallet.app.setupintegrationtests.BaseIntegrationTest
import com.mateuszcholyn.wallet.frontend.view.screen.backup.backupV1.BackupWalletV1
import com.mateuszcholyn.wallet.manager.ext.backup.impo.importV1UseCase
import com.mateuszcholyn.wallet.manager.randomBackupCategoryV1
import com.mateuszcholyn.wallet.manager.randomBackupExpenseV1
import com.mateuszcholyn.wallet.manager.validator.validate
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Test

@HiltAndroidTest
class ImportV1UseCaseTest : BaseIntegrationTest() {

    @Test
    fun shouldImportV1Data() {
        // given
        val givenRandomBackupCategoryV1 =
            randomBackupCategoryV1(
                expenses = listOf(randomBackupExpenseV1())
            )


        val givenBackupWalletV1 =
            BackupWalletV1(
                version = 1,
                categories = listOf(givenRandomBackupCategoryV1),
            )

        val manager = initExpenseAppManager {}

        // when
        val importSummary =
            manager.importV1UseCase {
                backupWalletV1 = givenBackupWalletV1
            }

        // then
        importSummary.validate {
            numberOfInputDataMatch(givenBackupWalletV1)
            numberOfImportedCategoriesEqualTo(1)
            numberOfSkippedCategoriesEqualTo(0)
            numberOfImportedExpensesEqualTo(1)
            numberOfSkippedExpensesEqualTo(0)
        }

        manager.validate {
            numberOfCoreCategoriesEqualTo(1)
            numberOfCoreExpensesEqualTo(1)
        }
    }

//    @Test
//    fun `should remove all expenses and then import from backup`() {
//        // given
//        val numberOfExpensesInBackupData = 5
//        val numberOfNewExpensesAfterBackup = 3
//        lateinit var categoryScope: CategoryScope
//
//        val manager =
//            initExpenseAppManager {
//                categoryScope = category {
//                    repeat(numberOfExpensesInBackupData) {
//                        expense {}
//                    }
//                }
//            }
//
//        // and backup is created
//        val givenBackupWalletV1 = manager.exportV1UseCase()
//
//        // new expenses are added
//        repeat(numberOfNewExpensesAfterBackup) {
//            manager.addExpenseUseCase {
//                categoryId = categoryScope.categoryId
//            }
//        }
//
//        // when import with parameter removeAllBeforeImport is set to true
//        val importV1Summary =
//            manager.importV1UseCase {
//                backupWalletV1 = givenBackupWalletV1
//                removeAllBeforeImport = true
//            }
//
//        // then
//        importV1Summary.validate {
//            numberOfInputDataMatch(givenBackupWalletV1)
//            numberOfImportedCategoriesEqualTo(1)
//            numberOfSkippedCategoriesEqualTo(0)
//            numberOfImportedExpensesEqualTo(numberOfExpensesInBackupData)
//            numberOfSkippedExpensesEqualTo(0)
//        }
//        manager.validate {
//            numberOfCoreExpensesEqualTo(numberOfExpensesInBackupData)
//        }
//    }
//
//    @Test
//    fun `should add a new category when database does not have category with given categoryId`() {
//        // given
//        lateinit var existingCategoryScope: CategoryScope
//        val manager =
//            initExpenseAppManager {
//                existingCategoryScope = category {}
//            }
//
//        val givenCategoryIdFromBackupFile = randomCategoryId()
//        val givenRandomBackupCategoryV1 =
//            randomBackupCategoryV1(categoryId = givenCategoryIdFromBackupFile)
//
//        val givenBackupWalletV1 =
//            BackupWalletV1(
//                version = 1,
//                categories = listOf(givenRandomBackupCategoryV1),
//                expenses = emptyList(),
//            )
//
//        // when
//        val importV1Summary =
//            manager.importV1UseCase {
//                backupWalletV1 = givenBackupWalletV1
//            }
//
//        // then
//        importV1Summary.validate {
//            numberOfInputDataMatch(givenBackupWalletV1)
//            numberOfImportedCategoriesEqualTo(1)
//            numberOfSkippedCategoriesEqualTo(0)
//            numberOfImportedExpensesEqualTo(0)
//            numberOfSkippedExpensesEqualTo(0)
//        }
//        manager.validate {
//            numberOfCoreCategoriesEqualTo(2)
//        }
//        val firstCategory = manager.getAllCoreCategories().first()
//        assert(firstCategory.id == existingCategoryScope.categoryId)
//
//        val lastCategory = manager.getAllCoreCategories().last()
//        assert(lastCategory.id == givenCategoryIdFromBackupFile)
//    }
//
//    @Test
//    fun `should not create a new category when database have category with given categoryId`() {
//        // given
//        lateinit var existingCategoryScope: CategoryScope
//        val manager =
//            initExpenseAppManager {
//                existingCategoryScope = category {}
//            }
//
//        val givenRandomBackupCategoryV1 =
//            randomBackupCategoryV1(categoryId = existingCategoryScope.categoryId)
//
//        val givenBackupWalletV1 =
//            BackupWalletV1(
//                version = 1,
//                categories = listOf(givenRandomBackupCategoryV1),
//                expenses = emptyList(),
//            )
//
//
//        // when
//        val importV1Summary =
//            manager.importV1UseCase {
//                backupWalletV1 = givenBackupWalletV1
//            }
//
//        // then
//        importV1Summary.validate {
//            numberOfInputDataMatch(givenBackupWalletV1)
//            numberOfImportedCategoriesEqualTo(0)
//            numberOfSkippedCategoriesEqualTo(1)
//            numberOfImportedExpensesEqualTo(0)
//            numberOfSkippedExpensesEqualTo(0)
//        }
//        manager.validate {
//            numberOfCoreCategoriesEqualTo(1)
//        }
//        assert(
//            manager
//                .getAllCoreCategories()
//                .first()
//                .id == existingCategoryScope.categoryId
//        )
//    }
//
//    @Test
//    fun importedDataContainsPaidAtInInstantFormat() {
//        // given
//        val givenRandomBackupCategoryV1 = randomBackupCategoryV1()
//        val givenRandomBackupExpenseV1 =
//            randomBackupExpenseV1(categoryId = CategoryId(givenRandomBackupCategoryV1.id))
//
//        val givenBackupWalletV1 =
//            BackupWalletV1(
//                version = 1,
//                categories = listOf(givenRandomBackupCategoryV1),
//                expenses = listOf(givenRandomBackupExpenseV1),
//            )
//
//        val manager = initExpenseAppManager {}
//
//        // when
//        manager.importV1UseCase {
//            backupWalletV1 = givenBackupWalletV1
//        }
//
//        // then
//        val expenseFromDb =
//            manager
//                .expenseAppDependencies
//                .expenseRepositoryV2
//                .getAllExpenses()
//                .first()
//
//        assertInstant(
//            expenseFromDb.paidAt,
//            InstantConverter.toInstant(givenBackupWalletV1.expenses.first().paidAt),
//        ) {
//            """Expected date as: ${InstantConverter.toInstant(givenBackupWalletV1.expenses.first().paidAt)}.
//                Got: ${expenseFromDb.paidAt}
//                """
//        }
//    }
//
//    @Test
//    fun `should not add a new expense when expense in db have the same expenseId`() {
//        // given
//        lateinit var existingCategoryScope: CategoryScope
//        lateinit var existingExpenseScope: ExpenseScope
//        val manager =
//            initExpenseAppManager {
//                existingCategoryScope = category {
//                    existingExpenseScope = expense { }
//                }
//            }
//
//        val givenRandomBackupCategoryV1 =
//            randomBackupCategoryV1(categoryId = existingCategoryScope.categoryId)
//
//        val givenRandomBackupExpenseV1 =
//            randomBackupExpenseV1(
//                expenseId = existingExpenseScope.expenseId,
//                categoryId = CategoryId(givenRandomBackupCategoryV1.id),
//            )
//
//        val givenBackupWalletV1 =
//            BackupWalletV1(
//                version = 1,
//                categories = listOf(givenRandomBackupCategoryV1),
//                expenses = listOf(givenRandomBackupExpenseV1),
//            )
//
//
//        // when
//        val importV1Summary =
//            manager.importV1UseCase {
//                backupWalletV1 = givenBackupWalletV1
//            }
//
//        // then
//        importV1Summary.validate {
//            numberOfInputDataMatch(givenBackupWalletV1)
//            numberOfImportedCategoriesEqualTo(0)
//            numberOfSkippedCategoriesEqualTo(1)
//            numberOfImportedExpensesEqualTo(0)
//            numberOfSkippedExpensesEqualTo(1)
//        }
//        manager.validate {
//            numberOfCoreExpensesEqualTo(1)
//        }
//        val expenseFromDb = manager.getAllCoreExpenses().first()
//        assert(givenRandomBackupExpenseV1.expenseId == expenseFromDb.expenseId.id)
//    }
//
//    @Test
//    fun `should add a new expense when expense in db have the different expenseId from expense from import file`() {
//        // given
//        lateinit var existingCategoryScope: CategoryScope
//        val manager =
//            initExpenseAppManager {
//                existingCategoryScope = category {
//                    expense { }
//                }
//            }
//
//        val givenRandomBackupCategoryV1 =
//            randomBackupCategoryV1(categoryId = existingCategoryScope.categoryId)
//
//        val givenRandomBackupExpenseV1 =
//            randomBackupExpenseV1(categoryId = existingCategoryScope.categoryId)
//
//        val givenBackupWalletV1 =
//            BackupWalletV1(
//                version = 1,
//                categories = listOf(givenRandomBackupCategoryV1),
//                expenses = listOf(givenRandomBackupExpenseV1),
//            )
//
//        // when
//        val importV1Summary =
//            manager.importV1UseCase {
//                backupWalletV1 = givenBackupWalletV1
//            }
//
//        // then
//        importV1Summary.validate {
//            numberOfInputDataMatch(givenBackupWalletV1)
//            numberOfImportedCategoriesEqualTo(0)
//            numberOfSkippedCategoriesEqualTo(1)
//            numberOfImportedExpensesEqualTo(1)
//            numberOfSkippedExpensesEqualTo(0)
//        }
//        manager.validate {
//            numberOfCoreExpensesEqualTo(2)
//        }
//        val expenseFromDb = manager.getAllCoreExpenses().last()
//
//        assertExpenseFromImportFileEqualToExpenseFromDb(
//            givenRandomBackupExpenseV1,
//            expenseFromDb,
//        )
//    }
//
//    @Test
//    fun afterSecondTimeImportAllExpensedAndCategoriesShouldBeMarkedAsSkipped() {
//        // given
//        val givenRandomBackupCategoryV1 = randomBackupCategoryV1()
//
//        val givenBackupWalletV1 =
//            BackupWalletV1(
//                version = 1,
//                categories = listOf(givenRandomBackupCategoryV1),
//                expenses = listOf(
//                    randomBackupExpenseV1(
//                        categoryId = CategoryId(
//                            givenRandomBackupCategoryV1.id
//                        )
//                    )
//                ),
//            )
//
//        val manager = initExpenseAppManager {}
//
//        // when
//        val firstImportSummary =
//            manager.importV1UseCase {
//                backupWalletV1 = givenBackupWalletV1
//            }
//
//        // then
//        firstImportSummary.validate {
//            numberOfInputDataMatch(givenBackupWalletV1)
//            numberOfImportedCategoriesEqualTo(1)
//            numberOfSkippedCategoriesEqualTo(0)
//            numberOfImportedExpensesEqualTo(1)
//            numberOfSkippedExpensesEqualTo(0)
//        }
//
//        // when import again
//        val secondImportSummary =
//            manager.importV1UseCase {
//                backupWalletV1 = givenBackupWalletV1
//            }
//
//        // then
//        secondImportSummary.validate {
//            numberOfInputDataMatch(givenBackupWalletV1)
//            numberOfImportedCategoriesEqualTo(0)
//            numberOfSkippedCategoriesEqualTo(1)
//            numberOfImportedExpensesEqualTo(0)
//            numberOfSkippedExpensesEqualTo(1)
//        }
//
//    }

}
