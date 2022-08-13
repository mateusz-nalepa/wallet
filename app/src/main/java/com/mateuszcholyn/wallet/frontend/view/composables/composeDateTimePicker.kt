package com.mateuszcholyn.wallet.frontend.view.composables

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.mateuszcholyn.wallet.R
import com.mateuszcholyn.wallet.util.localDateTimeUtils.toHumanText
import com.mateuszcholyn.wallet.util.localDateTimeUtils.toLocalDateTime
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.MaterialDialogState
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalDateTime

@Composable
fun ComposeDateTimePicker(
    dialogState: MaterialDialogState,
    value: String,
    onValueChange: (String) -> Unit,
) {
    val toLocalDateTime = value.toLocalDateTime()
    var newDate = toLocalDateTime.toLocalDate()

    val timePickerDialogState = rememberMaterialDialogState()

    MaterialDialog(
        dialogState = timePickerDialogState,
        buttons = {
            positiveButton(stringResource(R.string.datePickerOk))
            negativeButton(stringResource(R.string.datePickerCancel))
        }
    )
    {
        timepicker(is24HourClock = true) { newTime ->

            LocalDateTime
                .of(newDate, newTime)
                .toHumanText()
                .also(onValueChange)
        }
    }

    MaterialDialog(
        dialogState = dialogState,
        buttons = {
            positiveButton(stringResource(R.string.datePickerOk))
            negativeButton(stringResource(R.string.datePickerCancel))
        },
    )
    {
        datepicker { date ->
            newDate = date
            timePickerDialogState.show()
        }
    }

}