package com.mateuszcholyn.wallet.domain.category.mapper

import com.mateuszcholyn.wallet.domain.category.db.model.Category
import com.mateuszcholyn.wallet.domain.category.model.CategoryDto

class CategoryMapper {

    fun toEntity(categoryDto: CategoryDto): Category {
        return Category(
                name = categoryDto.name
        )
    }

    fun fromEntity(category: Category): CategoryDto {
        return CategoryDto(
                id = category.categoryId!!,
                name = category.name!!
        )
    }

}