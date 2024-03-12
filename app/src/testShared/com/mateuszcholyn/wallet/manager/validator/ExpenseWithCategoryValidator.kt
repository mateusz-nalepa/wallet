//package com.mateuszcholyn.wallet.manager.validator
//
//import com.mateuszcholyn.wallet.backend.api.core.expense.ExpenseWithCategory
//import com.mateuszcholyn.wallet.manager.CategoryScope
//import com.mateuszcholyn.wallet.manager.ExpenseScope
//import com.mateuszcholyn.wallet.manager.validator.LocalDateTimeValidator.assertInstant

// TODO: fix this xd
//fun ExpenseWithCategory.validate(
//    validateBlock: ExpenseWithCategoryValidator.() -> Unit,
//) {
//    ExpenseWithCategoryValidator(
//        expenseWithCategory = this,
//    ).apply(validateBlock)
//}
//
//
//class ExpenseWithCategoryValidator(
//    private val expenseWithCategory: ExpenseWithCategory,
//) {
//    fun equalTo(
//        categoryScope: CategoryScope,
//        expenseScope: ExpenseScope,
//    ) {
//        assert(expenseWithCategory.categoryId == categoryScope.categoryId) {
//            "Expected categoryId is: $${categoryScope.categoryId}. " +
//                    "Actual: ${expenseWithCategory.categoryId}"
//        }
//
//        assert(expenseWithCategory.categoryName == categoryScope.categoryName) {
//            "Expected category name is: ${categoryScope.categoryName}. " +
//                    "Actual: ${expenseWithCategory.categoryName}"
//        }
//
//        assert(expenseWithCategory.expenseId == expenseScope.expenseId) {
//            "Expected expenseId is: ${expenseScope.expenseId}. " +
//                    "Actual: ${expenseWithCategory.expenseId}"
//        }
//
//        assert(expenseWithCategory.description == expenseScope.description) {
//            "Expected description is: ${expenseScope.description}. " +
//                    "Actual: ${expenseWithCategory.description}"
//        }
//
//        assertInstant(expenseWithCategory.paidAt, expenseScope.paidAt) {
//            "Expected paidAt is: ${expenseScope.paidAt}. " +
//                    "Actual: ${expenseWithCategory.paidAt}"
//        }
//
//        assert(expenseWithCategory.amount == expenseScope.amount) {
//            "Expected amount is: ${expenseScope.amount}. " +
//                    "Actual: ${expenseWithCategory.amount}"
//        }
//    }
//}
