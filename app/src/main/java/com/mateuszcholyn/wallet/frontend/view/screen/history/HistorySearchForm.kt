package com.mateuszcholyn.wallet.frontend.view.screen.history

import com.mateuszcholyn.wallet.backend.api.core.category.CategoryId
import com.mateuszcholyn.wallet.backend.api.core.category.SubCategoryId
import com.mateuszcholyn.wallet.backend.api.searchservice.SearchCriteria
import com.mateuszcholyn.wallet.frontend.view.dropdown.GroupElement
import com.mateuszcholyn.wallet.frontend.view.dropdown.QuickRangeData
import com.mateuszcholyn.wallet.frontend.view.dropdown.SortElement
import com.mateuszcholyn.wallet.frontend.view.screen.history.filters.CategoryView
import com.mateuszcholyn.wallet.frontend.view.util.toDoubleOrDefaultZero
import com.mateuszcholyn.wallet.util.localDateTimeUtils.fromUserLocalTimeZoneToUTCInstant
import java.time.LocalDateTime

data class HistorySearchForm(
    val advancedOptionsVisible: Boolean = false,

    val categoriesList: List<CategoryView> = emptyList(),
    val selectedCategory: CategoryView = CategoryView.default,

    val quickDataRanges: List<QuickRangeData> = emptyList(),
    val selectedQuickRangeData: QuickRangeData = QuickRangeData.default,

    val beginDate: LocalDateTime? = selectedQuickRangeData.beginDate,
    val endDate: LocalDateTime? = selectedQuickRangeData.endDate,
    val showCustomRangeDates: Boolean = false,

    val sortElements: List<SortElement> = emptyList(),
    val selectedSortElement: SortElement = SortElement.default,

    val amountRangeStart: String = 0.toString(),
    val amountRangeEnd: String = Int.MAX_VALUE.toString(),
    //

    val groupingElements: List<GroupElement> = emptyList(),
    val selectedGroupingElement: GroupElement = GroupElement.default,
    val isGroupingEnabled: Boolean = false,
)

fun HistorySearchForm.toSearchCriteria(): SearchCriteria =
    SearchCriteria(
        categoryId = selectedCategory.categoryId?.let { CategoryId(it) },
        subCategoryId = selectedCategory.subCategoryId?.let { SubCategoryId(it) },
        beginDate = beginDate?.fromUserLocalTimeZoneToUTCInstant(),
        endDate = endDate?.fromUserLocalTimeZoneToUTCInstant(),
        fromAmount = amountRangeStart.toDoubleOrDefaultZero().toBigDecimal(),
        toAmount = amountRangeEnd.toDoubleOrDefaultZero().toBigDecimal(),
        sort = selectedSortElement.sort,
    )
