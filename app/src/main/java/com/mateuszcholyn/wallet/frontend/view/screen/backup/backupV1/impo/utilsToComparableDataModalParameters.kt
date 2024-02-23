package com.mateuszcholyn.wallet.frontend.view.screen.backup.backupV1.impo

import com.mateuszcholyn.wallet.R
import com.mateuszcholyn.wallet.frontend.domain.usecase.backup.impo.OnCategoryChangedInput
import com.mateuszcholyn.wallet.frontend.domain.usecase.backup.impo.OnExpanseChangedInput
import com.mateuszcholyn.wallet.frontend.view.screen.backup.ComparableData
import com.mateuszcholyn.wallet.frontend.view.screen.backup.ComparatorModalParameters
import com.mateuszcholyn.wallet.frontend.view.screen.history.orDefaultDescription
import com.mateuszcholyn.wallet.frontend.view.util.asPrintableAmount
import com.mateuszcholyn.wallet.util.localDateTimeUtils.fromUTCInstantToUserLocalTimeZone
import com.mateuszcholyn.wallet.util.localDateTimeUtils.toHumanDateTimeText

fun OnCategoryChangedInput.toComparableDataModalParameters(): ComparatorModalParameters =
    ComparatorModalParameters(
        titleKey = R.string.backupScreen_category_has_changed,
        leftValuesQuickSummaryKey = R.string.backupScreen_backup_state,
        rightValuesQuickSummaryKey = R.string.backupScreen_actual_state,

        keepLeftTextKey = R.string.backupScreen_use_data_from_backup,
        onKeepLeft = useCategoryFromBackup,

        keepRightTextKey = R.string.backupScreen_keep_existing,
        onKeepRight = keepCategoryFromDatabase,

        comparableData = listOf(
            ComparableData(
                R.string.backupScreen_category_name_label,
                categoriesToCompare.categoryFromBackup.categoryName,
                categoriesToCompare.categoryFromDatabase.categoryName,
            ),
        ),
    )

fun OnExpanseChangedInput.toComparableDataModalParameters(noDescriptionLabel: String): ComparatorModalParameters =
    ComparatorModalParameters(
        titleKey = R.string.backupScreen_expense_has_changed,
        leftValuesQuickSummaryKey = R.string.backupScreen_backup_state,
        rightValuesQuickSummaryKey = R.string.backupScreen_actual_state,

        keepLeftTextKey = R.string.backupScreen_use_data_from_backup,
        onKeepLeft = useExpenseFromBackup,

        keepRightTextKey = R.string.backupScreen_keep_existing,
        onKeepRight = keepExpenseFromDatabase,

        comparableData = listOf(
            ComparableData(
                R.string.common_category,
                expensesToCompare.expenseFromBackup.categoryName,
                expensesToCompare.expenseFromDatabase.categoryName,
            ),
            ComparableData(
                R.string.common_amount,
                expensesToCompare.expenseFromBackup.amount.asPrintableAmount(),
                expensesToCompare.expenseFromDatabase.amount.asPrintableAmount(),
            ),
            ComparableData(
                R.string.common_date,
                expensesToCompare.expenseFromBackup.paidAt.fromUTCInstantToUserLocalTimeZone().toHumanDateTimeText(),
                expensesToCompare.expenseFromDatabase.paidAt.fromUTCInstantToUserLocalTimeZone().toHumanDateTimeText(),
            ),
            ComparableData(
                R.string.common_description,
                expensesToCompare.expenseFromBackup.description.orDefaultDescription(noDescriptionLabel),
                expensesToCompare.expenseFromDatabase.description.orDefaultDescription(noDescriptionLabel),
            ),
        ),
    )
