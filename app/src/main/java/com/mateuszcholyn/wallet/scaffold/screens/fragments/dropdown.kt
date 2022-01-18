package com.mateuszcholyn.wallet.scaffold.screens.fragments

import androidx.compose.material.*
import androidx.compose.runtime.*
import com.mateuszcholyn.wallet.scaffold.util.defaultModifier


interface DropdownElement {
    val name: String
}

@ExperimentalMaterialApi
@Composable
fun <T> WalletDropdown(
        dropdownName: String,
        selectedElement: T,
        availableElements: List<T>,
        onItemSelected: (T) -> Unit,
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
                value = selectedElement.name,
                onValueChange = { },
                label = { Text(dropdownName) },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(
                            expanded = dropdownExpanded
                    )
                },
                colors = ExposedDropdownMenuDefaults.textFieldColors()
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
                            text = element.name,
                            modifier = defaultModifier,
                    )
                }
            }
        }
    }

}