package com.mateuszcholyn.wallet.domain.category

interface CategoryRepository {

    fun getAllOrderByUsageDesc(): List<Category>
    fun getAllWithDetailsOrderByUsageDesc(): List<CategoryDetails>
    fun getCategoryByName(name: String): Category
    fun remove(name: String): Boolean
    fun add(category: Category): Category

}