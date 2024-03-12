package com.mateuszcholyn.wallet.backend.impl.infrastructure.sqlite.searchservice

import com.mateuszcholyn.wallet.backend.api.searchservice.NewSort
import com.mateuszcholyn.wallet.backend.api.searchservice.SearchCriteria
import com.mateuszcholyn.wallet.backend.impl.infrastructure.sqlite.converters.BigDecimalDoubleTypeConverter.toDouble

object SearchServiceQueryHelper {

    fun prepareSearchQuery(
        searchCriteria: SearchCriteria,
    ): String {


        var averageQuery = """
                select * 
                from $SEARCH_SERVICE_TABLE_NAME
                """.trimIndent()


        val whereSections = mutableListOf<String>()

        if (searchCriteria.categoryId != null) {
            whereSections.add(" $CATEGORY_ID_FIELD_NAME = '${searchCriteria.categoryId.id}'")
        }

        if (searchCriteria.subCategoryId != null) {
            whereSections.add(" $SUB_CATEGORY_ID_FIELD_NAME = '${searchCriteria.subCategoryId.id}'")
        }

        if (searchCriteria.beginDate != null) {
            whereSections.add(" $PAID_AT_FIELD_NAME >= ${searchCriteria.beginDate.toEpochMilli()} ")
        }

        if (searchCriteria.endDate != null) {
            whereSections.add(" $PAID_AT_FIELD_NAME <= ${searchCriteria.endDate.toEpochMilli()} ")
        }

        if (searchCriteria.fromAmount != null) {
            whereSections.add(" $AMOUNT_FIELD_NAME >= ${toDouble(searchCriteria.fromAmount)} ")
        }

        if (searchCriteria.toAmount != null) {
            whereSections.add(" $AMOUNT_FIELD_NAME <= ${toDouble(searchCriteria.toAmount)} ")
        }

        if (searchCriteria.shouldAddWhereClause()) {
            averageQuery += whereSections.joinToString(prefix = " \n WHERE ", separator = " \n and")
        }
        averageQuery += " \n ORDER BY ${searchCriteria.resolveSort()}"
        return averageQuery
    }

    private fun SearchCriteria.resolveSort(): String =
        " ${sort.field.toFieldName()} ${sort.order.toOrderType()}"


    private fun NewSort.Field.toFieldName(): String {
        val s = when (this) {
            NewSort.Field.DATE -> PAID_AT_FIELD_NAME
            NewSort.Field.AMOUNT -> AMOUNT_FIELD_NAME
        }
        return s
    }

    private fun NewSort.Order.toOrderType(): String =
        when (this) {
            NewSort.Order.ASC -> "asc"
            NewSort.Order.DESC -> "desc"
        }
}


private fun SearchCriteria.shouldAddWhereClause(): Boolean =
    categoryId != null
            || beginDate != null
            || endDate != null
            || fromAmount != null
            || toAmount != null