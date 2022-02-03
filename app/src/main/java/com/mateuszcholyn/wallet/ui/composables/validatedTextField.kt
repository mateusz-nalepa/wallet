package com.mateuszcholyn.wallet.ui.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.mateuszcholyn.wallet.ui.util.defaultModifier

@Composable
fun ValidatedTextField(
        textFieldLabel: String = "Kwota",
        value: String,
        onValueChange: (String) -> Unit,
        valueInvalidText: String = "Niepoprawna wartość",
        isValueInValidFunction: (String) -> Boolean,
        keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        modifier: Modifier = defaultModifier,
) {

    var isValueInValid by remember { mutableStateOf(false) }

    Column {
        OutlinedTextField(
                value = value,
                onValueChange = {
                    isValueInValid = isValueInValidFunction(it)
                    onValueChange.invoke(it)
                },
                keyboardOptions = keyboardOptions,
                label = { Text(textFieldLabel) },
                modifier = modifier,
                singleLine = true,
                trailingIcon = {
                    if (isValueInValid) {
                        Icon(Icons.Filled.Error, "error")
                    }
                },
                isError = isValueInValid,
        )
        if (isValueInValid) {
            Text(
                    text = valueInvalidText,
                    color = MaterialTheme.colors.error,
                    style = MaterialTheme.typography.caption,
                    modifier = Modifier.padding(start = 16.dp)
            )
        }
    }

}