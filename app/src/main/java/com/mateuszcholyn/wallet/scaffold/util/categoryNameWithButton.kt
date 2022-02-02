package com.mateuszcholyn.wallet.scaffold.util

//@Composable
//fun CategoryNameWithButton() {
//    Column(modifier = defaultModifier) {
//        ValidatedTextField(
//                textFieldLabel = "Nazwa nowej kategorii",
//                value = categoryNameText,
//                onValueChange = { categoryNameText = it },
//                isValueInValidFunction = {
//                    categoryIsInvalid(it, categoryNamesOnly)
//                },
//                valueInvalidText = "Nieprawidłowa wartość",
//                modifier = defaultModifier.testTag("NewCategoryTextField"),
//        )
//        Button(
//                enabled = isFormValid,
//                onClick = {
//                    categoryService.add(Category(name = categoryNameText))
//                    categoryNameText = ""
//                    refreshCategoryList()
//                },
//                modifier = defaultButtonModifier.testTag("AddNewCategoryButton"),
//        ) {
//            Text("Dodaj kategorię")
//        }
//    }
//}