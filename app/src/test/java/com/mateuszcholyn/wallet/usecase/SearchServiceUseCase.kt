package com.mateuszcholyn.wallet.usecase

import com.mateuszcholyn.wallet.backend.searchservice.ExpensesList
import com.mateuszcholyn.wallet.backend.searchservice.SearchServiceAPI

class SearchServiceUseCase(
    private val searchServiceAPI: SearchServiceAPI,
) : UseCase {

    fun invoke(): ExpensesList =
        searchServiceAPI.getAll()

}