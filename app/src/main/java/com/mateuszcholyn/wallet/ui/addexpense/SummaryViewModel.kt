package com.mateuszcholyn.wallet.ui.addexpense

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SummaryViewModel : ViewModel() {
    val textSummaryLiveData = MutableLiveData<String>()
}