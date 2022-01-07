package com.mateuszcholyn.wallet.scaffold.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.mateuszcholyn.wallet.domain.category.CategoryService
import com.mateuszcholyn.wallet.scaffold.util.defaultButtonModifier
import com.mateuszcholyn.wallet.scaffold.util.defaultModifier
import com.mateuszcholyn.wallet.view.showShortText
import org.kodein.di.compose.rememberInstance

@Composable
fun HomeScreen() {

    val categoryService: CategoryService by rememberInstance()
    var text by remember { mutableStateOf(categoryService.getAllNamesOnly().first()) }
    var text2 by remember { mutableStateOf("Hello") }

    Column {
        Row(
                modifier = defaultModifier,
        ) {

            OutlinedTextField(
                    value = text,
                    onValueChange = { text = it },
                    label = { Text("Label") },
                    modifier = defaultModifier,
            )
        }

        Row(
                modifier = defaultModifier,
        ) {

            OutlinedTextField(
                    value = text2,
                    onValueChange = { text2 = it },
                    label = { Text("Label2") },
                    modifier = defaultModifier,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)

            )
        }

        Row(modifier = defaultModifier) {
            Button(
                    onClick = {
                        showShortText("CLICKED XD")
                    },
                    modifier = defaultButtonModifier,

                    ) {
                Text("Button")
            }
        }
    }


//    Button(
//            onClick = {
//                      showShortText("XDDDDDD")
//            },
//            // Uses ButtonDefaults.ContentPadding by default
//    ) {
//        // Inner content including an icon and a text label
//        Icon(
//                Icons.Filled.Favorite,
//                contentDescription = "Favorite",
//                modifier = Modifier.size(ButtonDefaults.IconSize)
//        )
//        Spacer(Modifier.size(ButtonDefaults.IconSpacing))
//        Text("Like")
//    }


//    Column(
//            modifier = Modifier
//                    .fillMaxSize()
//                    .background(colorResource(id = R.color.colorPrimaryDark))
//                    .wrapContentSize(Alignment.Center)
//    ) {
//        Text(
//                text = "Home View",
//                fontWeight = FontWeight.Bold,
//                color = Color.White,
//                modifier = Modifier.align(Alignment.CenterHorizontally),
//                textAlign = TextAlign.Center,
//                fontSize = 25.sp
//        )
//    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}