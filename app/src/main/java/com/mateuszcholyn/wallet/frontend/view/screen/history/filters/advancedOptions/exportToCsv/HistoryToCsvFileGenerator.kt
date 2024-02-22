package com.mateuszcholyn.wallet.frontend.view.screen.history.filters.advancedOptions.exportToCsv

import com.mateuszcholyn.wallet.frontend.view.screen.history.HistorySuccessContent

interface HistoryToCsvGenerator {
    fun generate(historySuccessContent: HistorySuccessContent): String
}

class HistoryToCsvFileGenerator : HistoryToCsvGenerator {
    override fun generate(historySuccessContent: HistorySuccessContent): String {
        return "DUPA"
    }
}