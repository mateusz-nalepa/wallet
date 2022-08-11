package com.mateuszcholyn.wallet.newTests.usecase.getcategoriesquicksummary

import com.mateuszcholyn.wallet.newTests.setup.BaseIntegrationTest
import com.mateuszcholyn.wallet.tests.manager.*
import com.mateuszcholyn.wallet.tests.manager.ext.getCategoriesQuickSummaryUseCase
import com.mateuszcholyn.wallet.tests.manager.ext.removeCategoryUseCase
import com.mateuszcholyn.wallet.tests.manager.ext.removeExpenseUseCase
import com.mateuszcholyn.wallet.tests.manager.ext.updateExpenseUseCase
import com.mateuszcholyn.wallet.tests.manager.validator.validate
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Test

@HiltAndroidTest
class GetCategoriesCategoryQuickSummaryUseCaseIntegrationTest : BaseIntegrationTest() {

    @Test
    fun quickSummaryShouldHaveInformationAboutAddedCategory() {

        // given
        lateinit var categoryScope: CategoryScope
        val manager =
            initExpenseAppManager {
                categoryScope = category {
                }
            }

        // when
        val quickSummaryList = manager.getCategoriesQuickSummaryUseCase()

        // then
        quickSummaryList.validate {
            categoryId(categoryScope.categoryId) {
                doesNotHaveExpenses()
            }
        }
    }

    @Test
    fun quickSummaryShouldHaveInformationAboutCategoryAfterExpenseAddedEvent() {
        // given
        val givenNumberOfExpenses = randomInt()
        lateinit var categoryScope: CategoryScope
        val manager =
            initExpenseAppManager {
                categoryScope = category {
                    repeat(givenNumberOfExpenses) {
                        expense { }
                    }
                }
            }

        // when
        val quickSummaryList = manager.getCategoriesQuickSummaryUseCase()

        // then
        quickSummaryList.validate {
            categoryId(categoryScope.categoryId) {
                hasNumberOfExpensesEqualTo(givenNumberOfExpenses)
            }
        }
    }

    @Test
    fun quickSummaryShouldHaveInformationAboutCategoryAfterExpenseRemovedEvent() {
        // given
        val givenNumberOfExpenses = randomInt()
        lateinit var categoryScope: CategoryScope
        lateinit var lastExpenseScope: ExpenseScope
        val manager =
            initExpenseAppManager {
                categoryScope = category {
                    repeat(givenNumberOfExpenses) {
                        lastExpenseScope = expense { }
                    }
                }
            }

        manager.removeExpenseUseCase {
            expenseId = lastExpenseScope.expenseId
        }

        // when
        val quickSummaryList = manager.getCategoriesQuickSummaryUseCase()

        // then
        quickSummaryList.validate {
            categoryId(categoryScope.categoryId) {
                hasNumberOfExpensesEqualTo(givenNumberOfExpenses - 1)
            }
        }
    }

    @Test
    fun quickSummaryShouldNotHaveInformationAboutCategoryAfterUpdateExpenseEvent() {
        // given
        lateinit var oldCategoryScope: CategoryScope
        lateinit var newCategoryScope: CategoryScope
        lateinit var expenseScope: ExpenseScope
        val manager =
            initExpenseAppManager {
                oldCategoryScope = category {
                    expenseScope = expense {}
                }
                newCategoryScope = category {}
            }

        manager.updateExpenseUseCase {
            existingExpenseId = expenseScope.expenseId
            newCategoryId = newCategoryScope.categoryId
        }

        // when
        val quickSummaryList = manager.getCategoriesQuickSummaryUseCase()

        // then
        quickSummaryList.validate {
            categoryId(oldCategoryScope.categoryId) {
                hasNumberOfExpensesEqualTo(0)
            }
            categoryId(newCategoryScope.categoryId) {
                hasNumberOfExpensesEqualTo(1)
            }
        }
    }

    @Test
    fun quickSummaryShouldNotHaveInformationAboutCategoryAfterCategoryRemovedEvent() {
        // given
        lateinit var categoryScope: CategoryScope
        val manager =
            initExpenseAppManager {
                categoryScope = category {}
            }

        manager.removeCategoryUseCase {
            categoryId = categoryScope.categoryId
        }

        // when
        val quickSummaryList = manager.getCategoriesQuickSummaryUseCase()

        // then
        quickSummaryList.validate {
            categoryIdDoesNotExist(categoryScope.categoryId)
        }
    }

}