package com.mateuszcholyn.wallet.newcode.app.backend.searchservice

import com.mateuszcholyn.wallet.infrastructure.util.BigDecimalDoubleTypeConverter
import com.mateuszcholyn.wallet.tests.manager.randomAmount
import com.mateuszcholyn.wallet.tests.manager.randomCategoryId
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