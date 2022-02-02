package com.mateuszcholyn.wallet.scaffold.util

fun categoryIsInvalid(category: String, categories: List<String>): Boolean {
    return category.isBlank() || category in categories
}