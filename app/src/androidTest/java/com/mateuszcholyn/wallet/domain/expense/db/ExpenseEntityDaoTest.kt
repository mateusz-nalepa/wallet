package com.mateuszcholyn.wallet.domain.expense.db

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mateuszcholyn.wallet.database.DatabaseTestSpecification
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
internal class ExpenseEntityDaoTest : DatabaseTestSpecification() {
//
//    private lateinit var expenseDao: ExpenseDao
//    private lateinit var categoryDao: CategoryDao
//
//    @Before
//    fun setUp() {
//        expenseDao = db.expenseDao()
//        categoryDao = db.categoryDao()
//        initDb()
//    }
//
//    @Test
//    fun shouldAddExpense() {
//        //given
//        val expense = ExpenseEntity(
//            amount = 50.0,
//            fkCategoryId = 1L,
//            date = LocalDateTime(),
//            description = "Hodorek"
//        )
//
//        //when
//        val savedExpense = expenseDao.add(expense)
//
//        //then
//        assertEquals(1L, savedExpense)
//    }
//
//    @Test
//    fun shouldFetchAllExpenses() {
//        //given
//        val expenses = expenseDao.getAll()
//
//        //expect
//        expenses.isNotEmpty()
//    }
//
//    @Test
//    fun shouldGetExpenseWithCategory() {
//        //given
//        val expenseWithCategory = expenseDao.getExpenseWithCategory(1L)
//
//        //expect
//        assertEquals(1L, expenseWithCategory.expenseEntity.expenseId)
//    }
//
//    @Test
//    fun shouldGetAverageForAllCategories() {
//        //given
//        val averageSearchCriteria = AverageSearchCriteria(ALL_CATEGORIES, minDate, maxDate)
//        val averageQuery = ExpenseQueriesHelper().prepareAverageSearchQuery(averageSearchCriteria)
//
//        //when
//        val averageAmount = expenseDao.averageAmount(averageQuery)
//
//        //then
//        assertEquals(17.0, averageAmount)
//    }
//
//    @Test
//    fun shouldGetAverageForCategoryOne() {
//        //given
//        val averageSearchCriteria = AverageSearchCriteria("One", minDate, maxDate)
//        val averageQuery = ExpenseQueriesHelper().prepareAverageSearchQuery(averageSearchCriteria)
//
//        //when
//        val averageAmount = expenseDao.averageAmount(averageQuery)
//
//        //then
//        assertEquals(10.5, averageAmount)
//    }
//
//
//    private fun initDb() {
//        initCategory()
//        initExpense()
//    }
//
//    private fun initCategory() {
//        categoryDao.add(CategoryEntity(name = "One"))
//        categoryDao.add(CategoryEntity(name = "Two"))
//    }
//
//    private fun initExpense() {
//        expenseDao.add(
//            ExpenseEntity(
//                amount = 1.0,
//                fkCategoryId = 1L,
//                date = dateIn2019September,
//                description = "One"
//            )
//        )
//        expenseDao.add(
//            ExpenseEntity(
//                amount = 20.0,
//                fkCategoryId = 1L,
//                date = dateIn2019September,
//                description = "Two"
//            )
//        )
//        expenseDao.add(
//            ExpenseEntity(
//                amount = 30.0,
//                fkCategoryId = 2L,
//                date = dateIn2019August,
//                description = "Three"
//            )
//        )
//    }
//
//    companion object {
//
//        val dateIn2019August = LocalDateTime(2019, 8, 14, 0, 0, 0)
//        val dateIn2019September = LocalDateTime(2019, 9, 14, 0, 0, 0)
//    }

}
