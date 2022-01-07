package com.mateuszcholyn.wallet.scaffold.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
    val scope = rememberCoroutineScope()

    val categoryName = categoryViewModel.categoryName.observeAsMutableState(initial = "")
    var categoryNameText by remember { categoryName }

    val categoryListState = categoryViewModel.categoryList.observeAsMutableState(initial = emptyList())
    var categoryList by remember { categoryListState }

    Column {
        Row(
                modifier = defaultModifier,
        ) {

            OutlinedTextField(
                    value = categoryNameText,
                    onValueChange = { categoryNameText = it },
                    label = { Text("Nazwa nowej kategorii") },
                    modifier = defaultModifier,
            )
        }

        Row(modifier = defaultModifier) {
            Button(
                    onClick = {
                        scope.launch {
                            categoryViewModel.addCategoryXDDD(categoryNameText)
                        }
                    },
                    modifier = defaultButtonModifier,
            ) {
                Text("Dodaj kategorię")
            }
        }

        LazyColumn(
                modifier =
                Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 4.dp),

                ) {
            item {
                Text("Kategorie")
                Divider()
            }
            items(categoryList) { categoryModel ->
                ListItem(
                        text = { Text(categoryModel.name) },
                        trailing = {
                            IconButton(
                                    onClick = {
                                        showShortText("NOT IMPLEMENTED YET")
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
            }

        }


//        Column(
//                modifier =
//                Modifier
//                        .fillMaxWidth()
//                        .padding(horizontal = 8.dp),
//
//                ) {
//            Text("Kategorie")
//            Divider()
//            ListItem(
//                    text = { Text("1") },
//                    trailing = {
//                        IconButton(
//                                onClick = {
//                                    showShortText("Trwa usuwanie")
//                                }
//                        ) {
//                            Icon(
//                                    Icons.Filled.Delete,
//                                    contentDescription = null,
//                                    modifier = Modifier.size(32.dp),
//
//                                    )
//                        }
//
//                    }
//            )
//            Divider()
//            ListItem(
//                    text = { Text("2") },
//                    trailing = {
//                        IconButton(
//                                onClick = {
//                                    showShortText("Trwa usuwanie")
//                                }
//                        ) {
//                            Icon(
//                                    Icons.Filled.Delete,
//                                    contentDescription = null,
//                                    modifier = Modifier.size(32.dp),
//
//                                    )
//                        }
//
//                    }
//            )
//        }
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