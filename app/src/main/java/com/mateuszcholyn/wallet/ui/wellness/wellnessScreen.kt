package com.mateuszcholyn.wallet.ui.wellness

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun WellnessScreenRunner() {
    WellnessScreen()
}

@Composable
fun WellnessScreen(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        StatefulWaterCounter(modifier)
        StatefulWellnessTasksList(modifier)
    }
}

@Composable
fun StatefulWellnessTasksList(modifier: Modifier = Modifier) {
    val list = remember { getWellnessTasks().toMutableStateList() }
    WellnessTasksList(list = list, onCloseTask = { task -> list.remove(task) }, modifier)
}

@Composable
fun StatefulWaterCounter(modifier: Modifier = Modifier) {
    var count by rememberSaveable { mutableStateOf(0) }
    StatelessWaterCounter(count, { count++ }, modifier)
}

@Composable
fun StatelessWaterCounter(
        count: Int,
        onIncrement: () -> Unit,
        modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.padding(16.dp)) {
        if (count > 0) {
            Text("You've had $count glasses.")
        }
        Button(onClick = onIncrement, Modifier.padding(top = 8.dp), enabled = count < 10) {
            Text("Add one")
        }
    }
}

//Remember in composition
//@Composable
//fun WaterCounter(modifier: Modifier = Modifier) {
//
//    Column(modifier = modifier.padding(16.dp)) {
//        var count by remember { mutableStateOf(0) }
//        if (count > 0) {
//            var showTask by remember { mutableStateOf(true) }
//            if (showTask) {
//                WellnessTaskItem(
//                        taskName = "Have you taken your 15 minute walk today?",
//                        onClose = { showTask = false },
//                )
//            }
//            Text("You've had $count glasses.")
//        }
//        Row(Modifier.padding(top = 8.dp)) {
//            Button(
//                    onClick = { count++ },
//                    enabled = count < 10
//            ) {
//                Text(text = "Add one")
//            }
//            Button(onClick = { count = 0 }, Modifier.padding(start = 8.dp)) {
//                Text("Clear water count")
//            }
//        }
//    }
//}

