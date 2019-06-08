package com.mateuszcholyn.wallet.category.service

import com.mateuszcholyn.wallet.category.db.CategoryExecutor
import com.mateuszcholyn.wallet.category.model.CategoryDto

class CategoryService(private val categoryExecutor: CategoryExecutor) {

    fun addNewCategory(categoryDto: CategoryDto) =
            categoryExecutor.saveNewCategory(categoryDto)

    fun getCategoryId(categoryName: String) =
            categoryExecutor.getCategoryId(categoryName)

    fun getAllNamesOnly() =
            getAll().map { it.name }

    fun getAll() =
            categoryExecutor.getAll()

    fun hardRemove(category: String) =
            categoryExecutor.hardRemove(category)

}