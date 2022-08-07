package com.mateuszcholyn.wallet.app.usecase.searchservice

import com.mateuszcholyn.wallet.app.backend.searchservice.SearchCriteria
import com.mateuszcholyn.wallet.app.backend.searchservice.SearchServiceAPI
import com.mateuszcholyn.wallet.app.backend.searchservice.SearchServiceResult
import com.mateuszcholyn.wallet.app.usecase.UseCase

class SearchServiceUseCase(
    private val searchServiceAPI: SearchServiceAPI,
) : UseCase {

    fun invoke(searchCriteria: SearchCriteria): SearchServiceResult =
        searchServiceAPI.getAll(searchCriteria)

}