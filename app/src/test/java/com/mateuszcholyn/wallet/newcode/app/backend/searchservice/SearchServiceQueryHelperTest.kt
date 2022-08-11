package com.mateuszcholyn.wallet.newcode.app.backend.searchservice

import com.mateuszcholyn.wallet.newcode.app.backend.core.category.CategoryId
import com.mateuszcholyn.wallet.tests.manager.randomAmount
import org.junit.Test

class SearchServiceQueryHelperTest {

    @Test
    fun shouldPrepareSearchQueryWhenSearchingByTwoParameters() {
        // given
        val searchCriteria =
            SearchCriteria(
                categoryId = CategoryId("5"),
                fromAmount = randomAmount(),
            )

        // when
        val query = SearchServiceQueryHelper.prepareSearchQuery(searchCriteria)

        // then
        assert(query == "select * \n" +
                "from search_service \n" +
                " WHERE  category_id = '5' \n" +
                " and amount >= 3.0  \n" +
                " ORDER BY  paid_at desc")
    }
}