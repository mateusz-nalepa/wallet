package com.mateuszcholyn.wallet.frontend.view.screen.history.filters

import androidx.annotation.StringRes
import com.mateuszcholyn.wallet.R
import com.mateuszcholyn.wallet.frontend.view.dropdown.DropdownElement

data class CategoryView(
    override val name: String? = null,
    override val subName: String? = null,
    @StringRes
    override val nameKey: Int? = null,
    val categoryId: String? = null,
    val subCategoryId: String? = null,
) : DropdownElement {
    companion object {
        val default =
            CategoryView(
                name = null,
                subName = null,
                nameKey = R.string.history_screen_allCategories,
                categoryId = null,
            )
    }
}

