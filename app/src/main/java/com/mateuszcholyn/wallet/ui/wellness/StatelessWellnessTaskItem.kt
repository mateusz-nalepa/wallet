package com.mateuszcholyn.wallet.ui.wellness

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Checkbox
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

data class WellnessTask(
        val id: Int,
        val label: String,
)

fun getWellnessTasks() = List(30) { i -> WellnessTask(i, "Task # $i") }

@Composable
fun WellnessTasksList(
        list: List<WellnessTask>,
        onCloseTask: (WellnessTask) -> Unit,
        modifier: Modifier = Modifier,
) {
    LazyColumn(modifier = modifier) {
        items(
                items = list,
                key = { task -> task.id },
        ) { task ->
            WellnessTaskItem(taskName = task.label, onCloseTask = { onCloseTask(task) })
        }
    }
}

@Composable
fun WellnessTaskItem(
        taskName: String,
        modifier: Modifier = Modifier,
        onCloseTask: () -> Unit,
) {
    var checkedState by rememberSaveable { mutableStateOf(false) }

    StatelessWellnessTaskItem(
            taskName = taskName,
            checked = checkedState,
            onCheckedChange = { newValue -> checkedState = newValue },
            onClose = onCloseTask,
            modifier = modifier,
    )
}

@Composable
private fun StatelessWellnessTaskItem(
        taskName: String,
        checked: Boolean,
        onCheckedChange: (Boolean) -> Unit,
        onClose: () -> Unit,
        modifier: Modifier = Modifier,
) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        Text(text = taskName, modifier = Modifier
                .weight(1f)
                .padding(start = 16.dp)
        )
        Checkbox(checked = checked, onCheckedChange = onCheckedChange)
        IconButton(onClick = onClose) {
            Icon(Icons.Filled.Close, contentDescription = "Close")
        }
    }
}