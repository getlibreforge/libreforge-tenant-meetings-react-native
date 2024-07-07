package com.libreforge.calendar.presentation.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.libreforge.calendar.R
import com.libreforge.calendar.presentation.ui.theme.ApplicationTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownMenu(
    options: List<String>, label: String, selectedOption: String, onOptionSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = !expanded }) {
        TextField(value = selectedOption,
            onValueChange = {},
            readOnly = true,
            label = { Text(label) },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expanded
                )
            },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
        )
        ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            options.forEach { selectionOption ->
                DropdownMenuItem(text = { Text(selectionOption) }, onClick = {
                    onOptionSelected(selectionOption)
                    expanded = false
                })
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DropdownMenuPreview() {
    val serverOptions = listOf(
        stringResource(id = R.string.lbl_server_option1),
        stringResource(id = R.string.lbl_server_option2)
    )
    var selectedServer by remember { mutableStateOf(serverOptions[0]) }

    ApplicationTheme {
        DropdownMenu(options = serverOptions,
            label = stringResource(id = R.string.lbl_server),
            selectedOption = selectedServer,
            onOptionSelected = { selectedServer = it })
    }
}