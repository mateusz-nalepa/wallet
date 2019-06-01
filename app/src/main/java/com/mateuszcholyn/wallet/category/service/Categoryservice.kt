package com.mateuszcholyn.wallet.category.service

import android.content.Context
import com.mateuszcholyn.wallet.category.db.CategoryExecutor
import com.mateuszcholyn.wallet.category.model.CategoryDto

class Categoryservice(val context: Context) {

    private val categoryExecutor: CategoryExecutor = CategoryExecutor(context)

    fun saveNewCategory(categoryDto: CategoryDto): CategoryDto =
            categoryExecutor.saveNewCategory(categoryDto)

    fun getCategoryId(categoryName: String): Long =
            categoryExecutor.getCategoryId(categoryName)

}