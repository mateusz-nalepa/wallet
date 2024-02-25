package com.mateuszcholyn.wallet.frontend.view.screen.history.filters.advancedOptions.exportToCsv

import com.mateuszcholyn.wallet.backend.api.searchservice.SearchSingleResult
import com.mateuszcholyn.wallet.frontend.view.util.EMPTY_STRING
import com.mateuszcholyn.wallet.manager.randomCategoryId
import com.mateuszcholyn.wallet.manager.randomExpenseId
import com.mateuszcholyn.wallet.util.localDateTimeUtils.fromUserLocalTimeZoneToUTCInstant
import com.mateuszcholyn.wallet.util.localDateTimeUtils.plusDays
import io.kotest.matchers.shouldBe
import org.junit.Test
import java.math.BigDecimal
import java.time.Instant
import java.time.LocalDateTime


val testCsvFileLabels =
    CsvFileLabels(
        categoryNameLabel = "Category",
        amountLabel = "Amount",
        descriptionLabel = "Description",
        paidAtLabel = "Paid at",
        exportTitleLabel = "Export data",
        fileNamePrefix = "simple-wallet-csv",
    )

class HistoryToCsvFileGeneratorTest {

    private val generator = HistoryToCsvFileGenerator()

    @Test
    fun `should generate only header`() {
        // given
        val headerContent =
            generator.generate(
                testCsvFileLabels,
                expensesList = emptyList(),
            )

        // expect
        headerContent shouldBe "Category,Amount,Description,Paid at\n"
    }


    @Test
    fun `generate csv file`() {
        // given
        val date = LocalDateTime.of(2024, 2, 23, 22, 15).fromUserLocalTimeZoneToUTCInstant()

        // when
        val csvFileContent =
            generator.generate(
                testCsvFileLabels,
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
        csvFileContent shouldBe "Category,Amount,Description,Paid at\n" +
            "categoryXD,\"50,00\",,23.02.2024 22:15\n" +
            "categoryXD 2,\"55,00\",some description,01.03.2024 22:15"
    }

    @Test
    fun `should wrapInQuoteIfHasComa`() {
        // change , to ","
        // given
        val date = LocalDateTime.of(2024, 2, 23, 22, 15).fromUserLocalTimeZoneToUTCInstant()

        // when
        val csvFileContent =
            generator.generate(
                testCsvFileLabels,
                expensesList = listOf(
                    createSearchSingleResult(
                        categoryName = "categoryXD,2",
                        amount = BigDecimal("55"),
                        paidAt = date,
                        description = "some,desc",
                    )
                )
            )

        // then
        csvFileContent shouldBe "Category,Amount,Description,Paid at\n" +
            "\"categoryXD,2\",\"55,00\",\"some,desc\",23.02.2024 22:15"
    }

    @Test
    fun `should replaceNewLineWithSpace`() {
        // change new line to ""
        // given
        val date = LocalDateTime.of(2024, 2, 23, 22, 15).fromUserLocalTimeZoneToUTCInstant()

        // when
        val csvFileContent =
            generator.generate(
                testCsvFileLabels,
                expensesList = listOf(
                    createSearchSingleResult(
                        categoryName = "categoryXD\n2",
                        amount = BigDecimal("55"),
                        paidAt = date,
                        description = "some\ndesc",
                    )
                )
            )

        // then
        csvFileContent shouldBe "Category,Amount,Description,Paid at\n" +
            "categoryXD 2,\"55,00\",some desc,23.02.2024 22:15"
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
