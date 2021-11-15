package com.mateuszcholyn.wallet.ui.addexpense

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AddExpenseViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is add expense fragment"
    }
    val text: LiveData<String> = _text
}