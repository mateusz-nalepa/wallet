package com.mateuszcholyn.wallet.ui.composables.datapicker

import androidx.compose.foundation.clickable
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.mateuszcholyn.wallet.R
import com.mateuszcholyn.wallet.ui.composables.ComposeDateTimePicker
import com.mateuszcholyn.wallet.ui.util.defaultModifier
import com.mateuszcholyn.wallet.util.dateutils.toHumanText
import com.mateuszcholyn.wallet.util.dateutils.toLocalDateTime
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalDateTime

@Composable
fun OutlinedDatePickerField(
    value: LocalDateTime,
    onValueChange: (LocalDateTime) -> Unit,
) {
    val datePickerDialogState = rememberMaterialDialogState()
    ComposeDateTimePicker(
        dialogState = datePickerDialogState,
        value = value.toHumanText(),
        onValueChange = {
            onValueChange(it.toLocalDateTime())
        },
    )

    OutlinedTextField(
        value = value.toHumanText(),
        onValueChange = {},
        label = { Text(stringResource(R.string.date)) },
        modifier = defaultModifier.clickable {
            datePickerDialogState.show()
        },
        enabled = false,
    )
}