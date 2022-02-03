package com.mateuszcholyn.wallet

import com.mateuszcholyn.wallet.domain.category.Category
import com.mateuszcholyn.wallet.infrastructure.category.CategoryEntity
import java.util.*

fun randomString(): String = UUID.randomUUID().toString()

fun randomCategoryName(): String = "categoryName-${randomString()}"

fun randomNewCategoryEntity(): CategoryEntity =
        CategoryEntity(
                name = randomCategoryName()
        )

fun randomNewCategory(): Category =
        Category(
                name = randomCategoryName()
        )
