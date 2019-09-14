package com.mateuszcholyn.wallet.domain.category.service

import com.mateuszcholyn.wallet.domain.category.db.CategoryDao
import com.mateuszcholyn.wallet.domain.category.mapper.CategoryMapper
import com.mateuszcholyn.wallet.domain.category.model.CategoryDto

class CategoryService(private val categoryDao: CategoryDao) {

    private val categoryMapper = CategoryMapper()

    fun getByName(category: String): CategoryDto {
        return categoryDao.getCategoryByName(category).let {
            categoryMapper.fromEntity(it)
        }
    }

    fun add(categoryDto: CategoryDto): CategoryDto {
        return categoryDao.add(categoryMapper.toEntity(categoryDto))
                .let {
                    categoryDto.id = it
                    categoryDto
                }
    }

    fun getAllNamesOnly(): List<String> =
            getAll().map { it.name }

    fun getAll(): List<CategoryDto> {
        return categoryDao.getAll().map { categoryMapper.fromEntity(it) }
    }

    fun remove(category: String): Boolean {
        return categoryDao.remove(category) == 1
    }

}