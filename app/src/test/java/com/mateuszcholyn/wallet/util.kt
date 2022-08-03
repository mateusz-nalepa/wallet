package com.mateuszcholyn.wallet

import java.util.*

fun randomUUID(): String = UUID.randomUUID().toString()
fun randomCategoryName(): String = "categoryName-${randomUUID()}"