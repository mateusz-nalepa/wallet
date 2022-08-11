package com.mateuszcholyn.wallet.newcode.app.backend.searchservice

import com.mateuszcholyn.wallet.config.newDatabase.AMOUNT_FIELD_NAME
import com.mateuszcholyn.wallet.config.newDatabase.CATEGORY_ID_FIELD_NAME
import com.mateuszcholyn.wallet.config.newDatabase.PAID_AT_FIELD_NAME
import com.mateuszcholyn.wallet.config.newDatabase.SEARCH_SERVICE_TABLE_NAME
import com.mateuszcholyn.wallet.infrastructure.util.BigDecimalDoubleTypeConverter.toDouble
import com.mateuszcholyn.wallet.infrastructure.util.LocalDateTimeConverter.toLong

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

        if (searchCriteria.beginDate != null) {
            whereSections.add(" $PAID_AT_FIELD_NAME >= ${toLong(searchCriteria.beginDate)} ")
        }

        if (searchCriteria.endDate != null) {
            whereSections.add(" $PAID_AT_FIELD_NAME <= ${toLong(searchCriteria.endDate)} ")
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


    private fun NewSort.Field.toFieldName(): String =
        when (this) {
            NewSort.Field.DATE -> PAID_AT_FIELD_NAME
            NewSort.Field.AMOUNT -> AMOUNT_FIELD_NAME
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