package com.mateuszcholyn.wallet.frontend.view.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.mateuszcholyn.wallet.R
import com.mateuszcholyn.wallet.frontend.view.util.defaultModifier


@Composable
fun ValidatedTextFieldV2(
    textFieldLabel: String = stringResource(R.string.amount),
    value: String,
    onValueChange: (String) -> Unit,
    isValueInvalid: Boolean = false,
    valueInvalidText: String = stringResource(R.string.incorrectValue),
    keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
    modifier: Modifier = defaultModifier,
) {


    Column {
        OutlinedTextField(
            value = value,
            onValueChange = {
                onValueChange.invoke(it)
            },
            keyboardOptions = keyboardOptions,
            label = { Text(textFieldLabel) },
            modifier = modifier,
            singleLine = true,
            trailingIcon = {
                if (isValueInvalid) {
                    Icon(Icons.Filled.Error, stringResource(R.string.iconError))
                }
            },
            isError = isValueInvalid,
        )
        if (isValueInvalid) {
            Text(
                text = valueInvalidText,
                color = MaterialTheme.colors.error,
                style = MaterialTheme.typography.caption,
                modifier = Modifier.padding(start = 16.dp)
            )
        }
    }

}
