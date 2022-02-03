package com.mateuszcholyn.wallet.ui.composables

import androidx.compose.runtime.Composable
import com.mateuszcholyn.wallet.util.toHumanText
import com.mateuszcholyn.wallet.util.toLocalDateTime
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
            buttons =
            {
                positiveButton("Ok")
                negativeButton("Cancel")
            }
    )
    {
        timepicker(is24HourClock = true) { newTime ->
            println("Wybrales taka godzine: $newTime")

            LocalDateTime
                    .of(newDate, newTime)
                    .toHumanText()
                    .also(onValueChange)
        }
    }

    MaterialDialog(
            dialogState = dialogState,
            buttons = {
                positiveButton("Ok")
                negativeButton("Cancel")
            },
    )
    {
        datepicker { date ->
            println("Wybrales taka date: $date")
            newDate = date
            timePickerDialogState.show()
        }
    }

}