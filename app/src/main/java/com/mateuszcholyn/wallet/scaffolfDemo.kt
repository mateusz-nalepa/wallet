package com.mateuszcholyn.wallet

import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

fun MainActivity.useScaffold() {
    setContent {
        ScaffoldDemo()
    }
}

@Preview
@Composable
fun ScaffoldDemo() {
    val materialBlue700 = Color(0xFF1976D2)
    val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))
    Scaffold(
            scaffoldState = scaffoldState,
            topBar = { TopAppBar(title = { Text("TopAppBar") }, backgroundColor = materialBlue700) },
            floatingActionButtonPosition = FabPosition.End,
            floatingActionButton = {
                FloatingActionButton(onClick = {}) {
                    Text("X")
                }
            },
            drawerContent = {
                Text(text = "Run Calculator", style = MaterialTheme.typography.h5, modifier = Modifier.padding(16.dp))
            },
            content = { Text("BodyContent") },
            bottomBar = { BottomAppBar(backgroundColor = materialBlue700) { Text("BottomAppBar") } }
    )
}
