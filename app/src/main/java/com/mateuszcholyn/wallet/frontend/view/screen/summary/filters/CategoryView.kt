package com.mateuszcholyn.wallet.frontend.view.screen.summary.filters

import com.mateuszcholyn.wallet.R
import com.mateuszcholyn.wallet.frontend.view.dropdown.DropdownElement

data class CategoryView(
    override val name: String,
    override val nameKey: Int? = null,
    val categoryId: String? = null,
) : DropdownElement {
    companion object {
        val default =
            CategoryView(
                name = "Wszystkie kategorie",
                nameKey = R.string.summaryScreen_allCategories,
                categoryId = null,
            )
    }
}

