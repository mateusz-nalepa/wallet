package com.mateuszcholyn.wallet.frontend.view.composables.datapicker

import androidx.compose.foundation.clickable
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.mateuszcholyn.wallet.R
import com.mateuszcholyn.wallet.frontend.view.composables.ComposeDateTimePicker
import com.mateuszcholyn.wallet.frontend.view.util.defaultModifier
import com.mateuszcholyn.wallet.util.localDateTimeUtils.toHumanDateTimeText
import com.mateuszcholyn.wallet.util.localDateTimeUtils.toLocalDateTime
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalDateTime

@Composable
fun OutlinedDatePickerField(
    text: String = stringResource(R.string.date),
    value: LocalDateTime,
    onValueChange: (LocalDateTime) -> Unit,
    modifier: Modifier = Modifier,
) {
    val datePickerDialogState = rememberMaterialDialogState()
    ComposeDateTimePicker(
        dialogState = datePickerDialogState,
        value = value.toHumanDateTimeText(),
        onValueChange = {
            onValueChange(it.toLocalDateTime())
        },
    )

    OutlinedTextField(
        value = value.toHumanDateTimeText(),
        onValueChange = {},
        label = { Text(text = text) },
        modifier = defaultModifier.clickable {
            datePickerDialogState.show()
        }.then(modifier),
        enabled = false,
    )
}