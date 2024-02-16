package com.mateuszcholyn.wallet.frontend.view.screen.summary

import com.mateuszcholyn.wallet.backend.api.core.category.CategoryId
import com.mateuszcholyn.wallet.backend.api.searchservice.SearchCriteria
import com.mateuszcholyn.wallet.frontend.view.dropdown.GroupElement
import com.mateuszcholyn.wallet.frontend.view.dropdown.QuickRangeData
import com.mateuszcholyn.wallet.frontend.view.dropdown.SortElement
import com.mateuszcholyn.wallet.frontend.view.screen.summary.filters.CategoryView
import com.mateuszcholyn.wallet.frontend.view.util.toDoubleOrDefaultZero
import com.mateuszcholyn.wallet.util.localDateTimeUtils.fromUserLocalTimeZoneToUTCInstant

data class SummarySearchForm(
    val advancedFiltersVisible: Boolean = false,

    val categoriesList: List<CategoryView> = emptyList(),
    val selectedCategory: CategoryView = CategoryView.default,

    val quickDataRanges: List<QuickRangeData> = emptyList(),
    val selectedQuickRangeData: QuickRangeData = QuickRangeData.default,

    val sortElements: List<SortElement> = emptyList(),
    val selectedSortElement: SortElement = SortElement.default,

    val amountRangeStart: String = 0.toString(),
    val amountRangeEnd: String = Int.MAX_VALUE.toString(),
    //

    val groupingElements: List<GroupElement> = emptyList(),
    val selectedGroupingElement: GroupElement = GroupElement.default,
    val isGroupingEnabled: Boolean = false,
)

fun SummarySearchForm.toSearchCriteria(): SearchCriteria =
    SearchCriteria(
        categoryId = selectedCategory.categoryId?.let { CategoryId(it) },
        beginDate = selectedQuickRangeData.beginDate?.fromUserLocalTimeZoneToUTCInstant(),
        endDate = selectedQuickRangeData.endDate?.fromUserLocalTimeZoneToUTCInstant(),
        fromAmount = amountRangeStart.toDoubleOrDefaultZero().toBigDecimal(),
        toAmount = amountRangeEnd.toDoubleOrDefaultZero().toBigDecimal(),
        sort = selectedSortElement.sort,
    )
