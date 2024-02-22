package com.mateuszcholyn.wallet.frontend.view.screen.history.filters.advancedOptions.exportToCsv

import com.mateuszcholyn.wallet.backend.api.searchservice.SearchSingleResult
import com.mateuszcholyn.wallet.frontend.view.util.EMPTY_STRING
import com.mateuszcholyn.wallet.manager.randomCategoryId
import com.mateuszcholyn.wallet.manager.randomExpenseId
import com.mateuszcholyn.wallet.util.localDateTimeUtils.plusDays
import io.kotest.matchers.shouldBe
import org.junit.Test
import java.math.BigDecimal
import java.time.Instant

class HistoryToCsvFileGeneratorTest {

    private val headerNames =
        HeaderNames(
            "Category",
            "Paid at",
            "Description",
            "Amount",
        )

    private val generator = HistoryToCsvFileGenerator()

    @Test
    fun `should generate only header`() {
        // given
        val headerContent =
            generator.generate(
                headerNames,
                expensesList = emptyList(),
            )

        // expect
        headerContent shouldBe "Category;Amount;Paid at;Description\n"
    }


    @Test
    fun `generate csv file`() {
        // given
        val date = Instant.ofEpochSecond(1708645149)

        // when
        val csvFileContent =
            generator.generate(
                headerNames,
                expensesList = listOf(
                    createSearchSingleResult(
                        categoryName = "categoryXD",
                        amount = BigDecimal("50"),
                        paidAt = date,
                        description = EMPTY_STRING,
                    ),
                    createSearchSingleResult(
                        categoryName = "categoryXD 2",
                        amount = BigDecimal("55"),
                        paidAt = date.plusDays(7),
                        description = "some description",
                    )
                )
            )

        // then
        csvFileContent shouldBe """
            Category;Amount;Paid at;Description
            categoryXD;50.00 zł;23.02.2024 00:39;
            categoryXD 2;55.00 zł;01.03.2024 00:39;some description
        """.trimIndent()
    }

    @Test
    fun `should replace semicolon to pipe`() {
        // change ; to |
        // given
        val date = Instant.ofEpochSecond(1708645149)

        // when
        val csvFileContent =
            generator.generate(
                headerNames,
                expensesList = listOf(
                    createSearchSingleResult(
                        categoryName = "categoryXD;2",
                        amount = BigDecimal("55"),
                        paidAt = date,
                        description = "some;desc",
                    )
                )
            )

        // then
        csvFileContent shouldBe """
            Category;Amount;Paid at;Description
            categoryXD|2;55.00 zł;23.02.2024 00:39;some|desc
        """.trimIndent()
    }

}


private fun createSearchSingleResult(
    categoryName: String,
    amount: BigDecimal,
    paidAt: Instant,
    description: String,
): SearchSingleResult =
    SearchSingleResult(
        expenseId = randomExpenseId(),
        categoryId = randomCategoryId(),
        categoryName = categoryName,
        amount = amount,
        paidAt = paidAt,
        description = description,
    )
