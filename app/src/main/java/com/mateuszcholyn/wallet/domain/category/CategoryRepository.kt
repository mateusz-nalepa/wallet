package com.mateuszcholyn.wallet.domain.category

interface CategoryRepository {

    fun getAllOrderByUsageDesc(): List<Category>
    fun getAllWithDetailsOrderByUsageDesc(): List<CategoryDetails>
    fun remove(categoryId: Long): Boolean
    fun add(category: Category): Category
    fun update(category: Category): Category

}