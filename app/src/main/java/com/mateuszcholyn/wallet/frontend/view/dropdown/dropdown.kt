package com.mateuszcholyn.wallet.frontend.view.dropdown

import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.ExposedDropdownMenuDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import com.mateuszcholyn.wallet.frontend.view.util.defaultModifier


interface DropdownElement {
    val name: String?
    val subName: String?
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
//            value = resolveName(selectedElement.name, selectedElement.nameKey),
            value = resolveNameForSelectedValue(selectedElement),
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
//                        text = resolveName(element.name, element.nameKey),
                        text = resolveNameForSelectList(element),
                        modifier = defaultModifier,
                    )
                }
            }
        }
    }
}

@Composable
private fun resolveName(
    name: String?,
    nameKey: Int?,
): String =
    name
        ?: stringResource(id = nameKey!!)

@Composable
private fun resolveNameForSelectedValue(
    dropdownElement: DropdownElement,
): String =
    // case for subcategories only XD
    if (dropdownElement.subName != null) {
        val xd = dropdownElement.subName
        val xd2 = dropdownElement.name ?: stringResource(id = dropdownElement.nameKey!!)
        "$xd2 -> $xd"
    } else {
        // case when there is custom not translated text
        dropdownElement.nameKey
            ?.let { stringResource(it) }
            ?: dropdownElement.name!!
    }


@Composable
private fun resolveNameForSelectList(
    dropdownElement: DropdownElement,
): String =
    // case for subcategories only XD
    if (dropdownElement.subName != null) {
        val xd = dropdownElement.subName
        "\t\t\t$xd"
    } else {
        // case when there is custom not translated text
        dropdownElement.nameKey
            ?.let { stringResource(it) }
            ?: dropdownElement.name!!
    }
