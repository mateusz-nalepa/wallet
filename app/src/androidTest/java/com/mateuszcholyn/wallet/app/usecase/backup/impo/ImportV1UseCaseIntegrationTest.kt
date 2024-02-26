package com.mateuszcholyn.wallet.app.usecase.backup.impo

import com.mateuszcholyn.wallet.app.setupintegrationtests.BaseIntegrationTest
import com.mateuszcholyn.wallet.backend.api.core.category.CategoryId
import com.mateuszcholyn.wallet.backend.impl.infrastructure.sqlite.converters.InstantConverter
import com.mateuszcholyn.wallet.frontend.view.screen.backup.backupV1.BackupWalletV1
import com.mateuszcholyn.wallet.manager.CategoryScope
import com.mateuszcholyn.wallet.manager.ExpenseScope
import com.mateuszcholyn.wallet.manager.category
import com.mateuszcholyn.wallet.manager.expense
import com.mateuszcholyn.wallet.manager.ext.backup.export.exportV1UseCase
import com.mateuszcholyn.wallet.manager.ext.backup.impo.importV1UseCase
import com.mateuszcholyn.wallet.manager.ext.core.category.updateCategoryUseCase
import com.mateuszcholyn.wallet.manager.ext.core.expense.addExpenseUseCase
import com.mateuszcholyn.wallet.manager.ext.core.expense.updateExpenseUseCase
import com.mateuszcholyn.wallet.manager.getAllCoreCategories
import com.mateuszcholyn.wallet.manager.getAllCoreExpenses
import com.mateuszcholyn.wallet.manager.randomBackupCategoryV1
import com.mateuszcholyn.wallet.manager.randomBackupExpenseV1
import com.mateuszcholyn.wallet.manager.randomCategoryId
import com.mateuszcholyn.wallet.manager.randomCategoryName
import com.mateuszcholyn.wallet.manager.randomExpenseId
import com.mateuszcholyn.wallet.manager.validator.LocalDateTimeValidator.assertInstant
import com.mateuszcholyn.wallet.manager.validator.validate
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.runBlocking
import org.junit.Test

@HiltAndroidTest
class ImportV1UseCaseIntegrationTest : BaseIntegrationTest() {

    @Test
    fun shouldImportV1DataWhenDatabaseIsEmpty() {
        // given
        val givenRandomBackupExpense = randomBackupExpenseV1()

        val givenRandomBackupCategoryV1 =
            randomBackupCategoryV1(expenses = listOf(givenRandomBackupExpense))

        val givenBackupWalletV1 =
            BackupWalletV1(
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
        manager.getAllCoreCategories().first().validate {
            isSameAsCategoryFromBackup(givenRandomBackupCategoryV1)
        }
        manager.getAllCoreExpenses().first().validate {
            isSameAsExpenseFromBackup(
                givenRandomBackupExpense,
                CategoryId(givenRandomBackupCategoryV1.id)
            )
        }
    }

    @Test
    fun importSummaryShouldShowThatNothingIsImportedAfterSecondImport() {
        // given
        val givenBackupWalletV1 =
            BackupWalletV1(
                categories = listOf(
                    randomBackupCategoryV1(
                        expenses = listOf(randomBackupExpenseV1())
                    )
                ),
            )

        val manager = initExpenseAppManager {}

        // when
        val firstImportSummary =
            manager.importV1UseCase {
                backupWalletV1 = givenBackupWalletV1
            }

        // then
        firstImportSummary.validate {
            numberOfInputDataMatch(givenBackupWalletV1)
            numberOfImportedCategoriesEqualTo(1)
            numberOfSkippedCategoriesEqualTo(0)
            numberOfImportedExpensesEqualTo(1)
            numberOfSkippedExpensesEqualTo(0)
        }

        // when
        val secondImportSummary =
            manager.importV1UseCase {
                backupWalletV1 = givenBackupWalletV1
            }

        // then
        secondImportSummary.validate {
            numberOfInputDataMatch(givenBackupWalletV1)
            numberOfImportedCategoriesEqualTo(0)
            numberOfSkippedCategoriesEqualTo(1)
            numberOfImportedExpensesEqualTo(0)
            numberOfSkippedExpensesEqualTo(1)
        }
    }


    @Test
    fun shouldRemoveAllExpensesAndThenImportFromBackup() {
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
        val importV1Summary =
            manager.importV1UseCase {
                backupWalletV1 = givenBackupWalletV1
                removeAllBeforeImport = true
            }

        // then
        importV1Summary.validate {
            numberOfInputDataMatch(givenBackupWalletV1)
            numberOfImportedCategoriesEqualTo(1)
            numberOfSkippedCategoriesEqualTo(0)
            numberOfImportedExpensesEqualTo(numberOfExpensesInBackupData)
            numberOfSkippedExpensesEqualTo(0)
        }
        manager.validate {
            numberOfCoreExpensesEqualTo(numberOfExpensesInBackupData)
        }
    }

    @Test
    fun shouldAddANewCategoryNextToExistingCategoryFromDbWhenCategoryIdIsDifferent() {
        // given
        lateinit var existingCategoryScope: CategoryScope
        val manager =
            initExpenseAppManager {
                existingCategoryScope = category {}
            }

        val givenCategoryIdFromBackupFile = randomCategoryId()
        val givenRandomBackupCategoryV1 =
            randomBackupCategoryV1(
                categoryId = givenCategoryIdFromBackupFile
            )

        val givenBackupWalletV1 =
            BackupWalletV1(
                categories = listOf(givenRandomBackupCategoryV1),
            )

        // when
        val importV1Summary =
            manager.importV1UseCase {
                backupWalletV1 = givenBackupWalletV1
                removeAllBeforeImport = false
            }

        // then
        importV1Summary.validate {
            numberOfInputDataMatch(givenBackupWalletV1)
            numberOfImportedCategoriesEqualTo(1)
            numberOfSkippedCategoriesEqualTo(0)
            numberOfImportedExpensesEqualTo(0)
            numberOfSkippedExpensesEqualTo(0)
        }
        manager.validate {
            numberOfCoreCategoriesEqualTo(2)
        }
        manager.getAllCoreCategories().first().validate {
            isSameAsExistingCategory(existingCategoryScope)
        }
        manager.getAllCoreCategories().last().validate {
            isSameAsCategoryFromBackup(givenBackupWalletV1.categories.first())
        }
    }

    @Test
    fun shouldNotCreateNewCategoryWhenCategoryFromDatabaseAndBackupAreTheSame() {
        // given
        lateinit var existingCategoryScope: CategoryScope
        val manager =
            initExpenseAppManager {
                existingCategoryScope = category {}
            }

        val givenBackupWalletV1 = manager.exportV1UseCase()

        // when
        val importV1Summary =
            manager.importV1UseCase {
                backupWalletV1 = givenBackupWalletV1
            }

        // then
        importV1Summary.validate {
            numberOfInputDataMatch(givenBackupWalletV1)
            numberOfImportedCategoriesEqualTo(0)
            numberOfSkippedCategoriesEqualTo(1)
            numberOfImportedExpensesEqualTo(0)
            numberOfSkippedExpensesEqualTo(0)
        }
        manager.validate {
            numberOfCoreCategoriesEqualTo(1)
        }
        manager.getAllCoreCategories().first().validate {
            isSameAsExistingCategory(existingCategoryScope)
        }
    }

    @Test
    fun shouldAskUserWhatToDoIfCategoryNameChangedAfterExportAndThenKeepCategoryFromDatabase() {
        // given
        lateinit var existingCategoryScope: CategoryScope
        val manager =
            initExpenseAppManager {
                existingCategoryScope = category {}
            }

        val givenBackupWalletV1 = manager.exportV1UseCase()

        val givenUpdatedCategoryName = "new category name"
        manager.updateCategoryUseCase {
            this.existingCategoryId = existingCategoryScope.categoryId
            newName = givenUpdatedCategoryName
        }

        // when
        val importV1Summary =
            manager.importV1UseCase {
                backupWalletV1 = givenBackupWalletV1
                onCategoryChangedAction = { onCategoryChangedInput ->
                    onCategoryChangedInput.keepCategoryFromDatabase.invoke()
                }
            }

        // then
        importV1Summary.validate {
            numberOfInputDataMatch(givenBackupWalletV1)
            numberOfImportedCategoriesEqualTo(0)
            numberOfSkippedCategoriesEqualTo(1)
            numberOfImportedExpensesEqualTo(0)
            numberOfSkippedExpensesEqualTo(0)
        }
        manager.validate {
            numberOfCoreCategoriesEqualTo(1)
        }
        manager.getAllCoreCategories().first().validate {
            this.nameEqualTo(givenUpdatedCategoryName)
            this.idEqualTo(existingCategoryScope.categoryId)
        }
    }

    @Test
    fun shouldAskUserWhatToDoIfCategoryNameChangedAfterExportAndThenKeepCategoryFromBackup() {
        // given
        lateinit var existingCategoryScope: CategoryScope
        val manager =
            initExpenseAppManager {
                existingCategoryScope = category {}
            }

        val givenBackupWalletV1 = manager.exportV1UseCase()

        manager.updateCategoryUseCase {
            this.existingCategoryId = existingCategoryScope.categoryId
            newName = "new category name"
        }

        // when
        val importV1Summary =
            manager.importV1UseCase {
                backupWalletV1 = givenBackupWalletV1
                onCategoryChangedAction = { onCategoryChangedInput ->
                    onCategoryChangedInput.useCategoryFromBackup.invoke()
                }
            }

        // then
        importV1Summary.validate {
            numberOfInputDataMatch(givenBackupWalletV1)
            numberOfImportedCategoriesEqualTo(1)
            numberOfSkippedCategoriesEqualTo(0)
            numberOfImportedExpensesEqualTo(0)
            numberOfSkippedExpensesEqualTo(0)
        }
        manager.validate {
            numberOfCoreCategoriesEqualTo(1)
        }
        manager.getAllCoreCategories().first().validate {
            isSameAsCategoryFromBackup(givenBackupWalletV1.categories.first())
        }
    }

    @Test
    fun shouldAddANewExpenseNextToExistingExpenseFromDbWhenExpenseIsDifferent() {
        // given
        lateinit var existingCategoryScope: CategoryScope
        lateinit var existingExpenseScope: ExpenseScope
        val manager =
            initExpenseAppManager {
                existingCategoryScope = category {
                    existingExpenseScope = expense { }
                }
            }

        val givenExpenseIdFromBackupFile = randomExpenseId()

        val givenBackupWalletV1 =
            BackupWalletV1(
                categories = listOf(
                    randomBackupCategoryV1(
                        expenses = listOf(randomBackupExpenseV1(expenseId = givenExpenseIdFromBackupFile))
                    )
                ),
            )

        // when
        val importV1Summary =
            manager.importV1UseCase {
                backupWalletV1 = givenBackupWalletV1
                removeAllBeforeImport = false
            }

        // then
        importV1Summary.validate {
            numberOfInputDataMatch(givenBackupWalletV1)
            numberOfImportedExpensesEqualTo(1)
            numberOfSkippedExpensesEqualTo(0)
        }
        manager.validate {
            numberOfCoreExpensesEqualTo(2)
        }
        manager.getAllCoreExpenses().first().validate {
            isSameAsExpenseFromDatabase(existingExpenseScope, existingCategoryScope.categoryId)
        }
        val backupCategory = givenBackupWalletV1.categories.first()
        manager.getAllCoreExpenses().last().validate {
            isSameAsExpenseFromBackup(
                backupCategory.expenses.first(),
                CategoryId(backupCategory.id),
            )
        }
    }

    @Test
    fun shouldNotCreateNewExpenseWhenExpenseFromDatabaseAndBackupAreTheSame() {
        // given
        lateinit var existingCategoryScope: CategoryScope
        lateinit var existingExpenseScope: ExpenseScope
        val manager =
            initExpenseAppManager {
                existingCategoryScope = category {
                    existingExpenseScope = expense { }
                }
            }

        val givenBackupWalletV1 = manager.exportV1UseCase()

        // when
        val importV1Summary =
            manager.importV1UseCase {
                backupWalletV1 = givenBackupWalletV1
            }

        // then
        importV1Summary.validate {
            numberOfInputDataMatch(givenBackupWalletV1)
            numberOfImportedExpensesEqualTo(0)
            numberOfSkippedExpensesEqualTo(1)
        }
        manager.validate {
            numberOfCoreExpensesEqualTo(1)
        }
        manager.getAllCoreExpenses().first().validate {
            isSameAsExpenseFromDatabase(
                existingExpenseScope,
                existingCategoryScope.categoryId,
            )
        }
    }

    @Test
    fun shouldAskUserWhatToDoIfExpenseChangedAfterExportAndThenKeepExpenseFromDatabase() {
        // given
        lateinit var existingCategoryScope: CategoryScope
        lateinit var existingExpenseScope: ExpenseScope
        val manager =
            initExpenseAppManager {
                existingCategoryScope = category {
                    existingExpenseScope = expense { }
                }
            }

        val givenBackupWalletV1 = manager.exportV1UseCase()

        val givenUpdatedExpenseDescription = "new expense description"
        manager.updateExpenseUseCase {
            existingExpenseId = existingExpenseScope.expenseId
            newDescription = givenUpdatedExpenseDescription
            newCategoryId = existingCategoryScope.categoryId
        }

        // when
        val importV1Summary =
            manager.importV1UseCase {
                backupWalletV1 = givenBackupWalletV1
                onExpanseChangedAction = { onCategoryChangedInput ->
                    onCategoryChangedInput.keepExpenseFromDatabase.invoke()
                }
            }

        // then
        importV1Summary.validate {
            numberOfInputDataMatch(givenBackupWalletV1)
            numberOfImportedExpensesEqualTo(0)
            numberOfSkippedExpensesEqualTo(1)
        }
        manager.validate {
            numberOfCoreExpensesEqualTo(1)
        }
        manager.getAllCoreExpenses().first().validate {
            descriptionEqualTo(givenUpdatedExpenseDescription)
            idIsEqualTo(existingExpenseScope.expenseId)
        }
    }

    @Test
    fun shouldAskUserWhatToDoIfExpenseChangedAfterExportAndThenKeepExpenseFromBackup() {
        // given
        lateinit var existingCategoryScope: CategoryScope
        lateinit var existingExpenseScope: ExpenseScope
        val manager =
            initExpenseAppManager {
                existingCategoryScope = category {
                    existingExpenseScope = expense { }
                }
            }

        val givenBackupWalletV1 = manager.exportV1UseCase()

        manager.updateExpenseUseCase {
            this.existingExpenseId = existingExpenseScope.expenseId
            newDescription = "new description"
            newCategoryId = existingCategoryScope.categoryId
        }

        // when
        val importV1Summary =
            manager.importV1UseCase {
                backupWalletV1 = givenBackupWalletV1
                onExpanseChangedAction = { onCategoryChangedInput ->
                    onCategoryChangedInput.useExpenseFromBackup.invoke()
                }
            }

        // then
        importV1Summary.validate {
            numberOfInputDataMatch(givenBackupWalletV1)
            numberOfImportedExpensesEqualTo(1)
            numberOfSkippedExpensesEqualTo(0)
        }
        manager.validate {
            numberOfCoreExpensesEqualTo(1)
        }
        manager.getAllCoreExpenses().first().validate {
            isSameAsExpenseFromBackup(
                givenBackupWalletV1.categories.first().expenses.first(),
                CategoryId(givenBackupWalletV1.categories.first().id),
            )
        }
    }

    @Test
    fun importedDataContainsExpensePaidAtInInstantFormat() {
        runBlocking {
            // given
            val givenRandomBackupExpense = randomBackupExpenseV1()

            val givenRandomBackupCategoryV1 =
                randomBackupCategoryV1(expenses = listOf(givenRandomBackupExpense))

            val givenBackupWalletV1 =
                BackupWalletV1(
                    categories = listOf(givenRandomBackupCategoryV1),
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
                    .expenseRepository
                    .getAllExpenses()
                    .first()

            val expensePaidAtFromBackup = InstantConverter.toInstant(givenRandomBackupExpense.paidAt)

            assertInstant(
                expenseFromDb.paidAt,
                expensePaidAtFromBackup,
            ) {
                """Expected date as: ${expensePaidAtFromBackup}.
                Got: ${expenseFromDb.paidAt}
                """
            }
        }
    }

    @Test
    fun thereShouldBeTwoCategoriesWithSameNameAfterImport() {
        // given
        val givenCategoryName = randomCategoryName()
        val manager =
            initExpenseAppManager {
                category {
                    categoryName = givenCategoryName
                }
            }

        val givenBackupWalletV1 =
            BackupWalletV1(
                categories = listOf(
                    randomBackupCategoryV1(
                        categoryId = randomCategoryId(),
                        name = givenCategoryName,
                    )
                ),
            )

        // when
        val importV1Summary =
            manager.importV1UseCase {
                backupWalletV1 = givenBackupWalletV1
            }

        // then
        importV1Summary.validate {
            numberOfInputDataMatch(givenBackupWalletV1)
            numberOfImportedCategoriesEqualTo(1)
            numberOfSkippedCategoriesEqualTo(0)
        }
        manager.validate {
            numberOfCoreCategoriesEqualTo(2)
        }
        val firstCategory = manager.getAllCoreCategories().first()
        val secondCategory = manager.getAllCoreCategories().last()

        assert(firstCategory.id != secondCategory.id) { "categoryId equal" }
        assert(firstCategory.name == secondCategory.name) { "categoryName not equal" }
        assert(firstCategory.name == givenCategoryName) { "categoryName not equal" }
    }

}
