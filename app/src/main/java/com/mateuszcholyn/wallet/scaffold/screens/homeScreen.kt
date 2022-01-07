package com.mateuszcholyn.wallet.scaffold.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.mateuszcholyn.wallet.scaffold.util.defaultButtonModifier
import com.mateuszcholyn.wallet.scaffold.util.defaultModifier
import com.mateuszcholyn.wallet.ui.category.CategoryViewModel
import com.mateuszcholyn.wallet.view.showShortText
import kotlinx.coroutines.launch
import org.kodein.di.compose.rememberInstance

@ExperimentalMaterialApi
@Composable
fun HomeScreen() {

    val categoryViewModel: CategoryViewModel by rememberInstance()

    val xd = categoryViewModel.categoryName.observeAsMutableState(initial = "")
    var text by remember { xd }
    val scope = rememberCoroutineScope()

    Column {
        Row(
                modifier = defaultModifier,
        ) {

            OutlinedTextField(
                    value = text,
                    onValueChange = { text = it },
                    label = { Text("Nazwa nowej kategorii") },
                    modifier = defaultModifier,
            )
        }

        Row(modifier = defaultModifier) {
            Button(
                    onClick = {
                        scope.launch {
                            categoryViewModel.addCategoryXDDD(text)
                        }
                    },
                    modifier = defaultButtonModifier,
            ) {
                Text("Dodaj kategorię")
            }
        }

        Column(
                modifier =
                Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),

                ) {
            Text("Kategorie")
            Divider()
            ListItem(
                    text = { Text("1") },
                    trailing = {
                        IconButton(
                                onClick = {
                                    showShortText("Trwa usuwanie")
                                }
                        ) {
                            Icon(
                                    Icons.Filled.Delete,
                                    contentDescription = null,
                                    modifier = Modifier.size(32.dp),

                                    )
                        }

                    }
            )
            Divider()
            ListItem(
                    text = { Text("2") },
                    trailing = {
                        IconButton(
                                onClick = {
                                    showShortText("Trwa usuwanie")
                                }
                        ) {
                            Icon(
                                    Icons.Filled.Delete,
                                    contentDescription = null,
                                    modifier = Modifier.size(32.dp),

                                    )
                        }

                    }
            )
        }
    }
}

@ExperimentalMaterialApi
@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}

@Composable
fun <R, T : R> MutableLiveData<T>.observeAsMutableState(initial: R): MutableState<R> {
    val lifecycleOwner = LocalLifecycleOwner.current
    val state = remember { mutableStateOf(initial) }
    DisposableEffect(this, lifecycleOwner) {
        val observer = Observer<T> { state.value = it }
        observe(lifecycleOwner, observer)
        onDispose { removeObserver(observer) }
    }
    return state
}