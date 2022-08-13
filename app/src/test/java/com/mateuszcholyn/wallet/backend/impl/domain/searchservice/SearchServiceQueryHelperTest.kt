package com.mateuszcholyn.wallet.backend.impl.domain.searchservice

import com.mateuszcholyn.wallet.backend.api.searchservice.SearchCriteria
import com.mateuszcholyn.wallet.backend.impl.infrastructure.sqlite.converters.BigDecimalDoubleTypeConverter
import com.mateuszcholyn.wallet.manager.randomAmount
import com.mateuszcholyn.wallet.manager.randomCategoryId
import org.junit.Test

class SearchServiceQueryHelperTest {

    @Test
    fun shouldPrepareSearchQueryWhenSearchingByTwoParameters() {
        // given
        val givenAmount = randomAmount()
        val givenCategory = randomCategoryId()


        val searchCriteria =
            SearchCriteria(
                categoryId = givenCategory,
                fromAmount = givenAmount,
            )

        // when
        val query = SearchServiceQueryHelper.prepareSearchQuery(searchCriteria)

        // then
        assert(
            query == "select * \n" +
                    "from search_service \n" +
                    " WHERE  category_id = '${givenCategory.id}' \n" +
                    " and amount >= ${BigDecimalDoubleTypeConverter.toDouble(givenAmount)}  \n" +
                    " ORDER BY  paid_at desc"
        )
    }
}