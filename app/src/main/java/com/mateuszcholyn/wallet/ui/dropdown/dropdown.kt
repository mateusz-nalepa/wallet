package com.mateuszcholyn.wallet.ui.dropdown

import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import com.mateuszcholyn.wallet.ui.util.defaultModifier


interface DropdownElement {
    val name: String
    val nameKey: Int?
}


@ExperimentalMaterialApi
@Composable
fun <T> WalletDropdown(
    dropdownName: String,
    selectedElement: T,
    availableElements: List<T>,
    onItemSelected: (T) -> Unit,
    isEnabled: Boolean = true,
) where T : DropdownElement {
    var dropdownExpanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        modifier = defaultModifier,
        expanded = dropdownExpanded,
        onExpandedChange = {
            dropdownExpanded = !dropdownExpanded
        }
    ) {
        TextField(
            modifier = defaultModifier,
            readOnly = true,
            value = resolveName(selectedElement.name, selectedElement.nameKey),
            onValueChange = { },
            label = { Text(dropdownName) },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = dropdownExpanded
                )
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
            enabled = isEnabled,
        )
        ExposedDropdownMenu(
            modifier = defaultModifier,
            expanded = dropdownExpanded,
            onDismissRequest = {
                dropdownExpanded = false
            }
        ) {
            availableElements.forEach { element ->
                DropdownMenuItem(
                    modifier = defaultModifier,
                    onClick = {
                        onItemSelected.invoke(element)
                        dropdownExpanded = false
                    }
                ) {
                    Text(
                        text = resolveName(element.name, element.nameKey),
                        modifier = defaultModifier,
                    )
                }
            }
        }
    }

}

@Composable
private fun resolveName(name: String, nameKey: Int?): String {
    val resolvedName =
        if (name.contains("R.string")) {
            stringResource(nameKey!!)
        } else {
            name
        }
    return resolvedName
}