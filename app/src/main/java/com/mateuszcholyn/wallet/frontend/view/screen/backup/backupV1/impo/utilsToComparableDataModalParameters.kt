package com.mateuszcholyn.wallet.frontend.view.screen.backup.backupV1.impo

import com.mateuszcholyn.wallet.frontend.domain.usecase.backup.impo.OnCategoryChangedInput
import com.mateuszcholyn.wallet.frontend.domain.usecase.backup.impo.OnExpanseChangedInput
import com.mateuszcholyn.wallet.frontend.view.screen.backup.ComparableData
import com.mateuszcholyn.wallet.frontend.view.screen.backup.ComparatorModalParameters
import com.mateuszcholyn.wallet.frontend.view.screen.summary.orDefaultDescription
import com.mateuszcholyn.wallet.frontend.view.util.asPrintableAmount
import com.mateuszcholyn.wallet.util.localDateTimeUtils.fromUTCInstantToUserLocalTimeZone
import com.mateuszcholyn.wallet.util.localDateTimeUtils.toHumanDateTimeText

fun OnCategoryChangedInput.toComparableDataModalParameters(): ComparatorModalParameters =
    ComparatorModalParameters(
        title = "Kategoria się zmieniła! Co zrobić?",
        leftValuesQuickSummary = "Stan z kopii",
        rightValuesQuickSummary = "Aktualny stan",

        keepLeftText = "Użyj danych z kopii",
        onKeepLeft = useCategoryFromBackup,

        keepRightText = "Zachowaj istniejący",
        onKeepRight = keepCategoryFromDatabase,

        comparableData = listOf(
            ComparableData(
                "Nazwa",
                categoriesToCompare.categoryFromBackup.categoryName,
                categoriesToCompare.categoryFromDatabase.categoryName,
            ),
        ),
    )

fun OnExpanseChangedInput.toComparableDataModalParameters(): ComparatorModalParameters =
    ComparatorModalParameters(
        title = "Wydatek się zmienił! Co zrobić?",
        leftValuesQuickSummary = "Stan z kopii",
        rightValuesQuickSummary = "Aktualny stan",

        keepLeftText = "Użyj danych z kopii",
        onKeepLeft = useExpenseFromBackup,

        keepRightText = "Zachowaj istniejący",
        onKeepRight = keepExpenseFromDatabase,

        comparableData = listOf(
            ComparableData(
                "Kategoria",
                expensesToCompare.expenseFromBackup.categoryName,
                expensesToCompare.expenseFromDatabase.categoryName,
            ),
            ComparableData(
                "Kwota",
                expensesToCompare.expenseFromBackup.amount.asPrintableAmount(),
                expensesToCompare.expenseFromDatabase.amount.asPrintableAmount(),
            ),
            ComparableData(
                "Data",
                expensesToCompare.expenseFromBackup.paidAt.fromUTCInstantToUserLocalTimeZone().toHumanDateTimeText(),
                expensesToCompare.expenseFromDatabase.paidAt.fromUTCInstantToUserLocalTimeZone().toHumanDateTimeText(),
            ),
            ComparableData(
                "Opis",
                expensesToCompare.expenseFromBackup.description.orDefaultDescription("Brak opisu"),
                expensesToCompare.expenseFromDatabase.description.orDefaultDescription("Brak opisu"),
            ),
        ),
    )
