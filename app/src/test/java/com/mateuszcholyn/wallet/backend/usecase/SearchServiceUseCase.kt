package com.mateuszcholyn.wallet.backend.usecase

import com.mateuszcholyn.wallet.backend.searchservice.SearchCriteria
import com.mateuszcholyn.wallet.backend.searchservice.SearchServiceResult
import com.mateuszcholyn.wallet.backend.searchservice.SearchServiceAPI

class SearchServiceUseCase(
    private val searchServiceAPI: SearchServiceAPI,
) : UseCase {

    fun invoke(searchCriteria: SearchCriteria): SearchServiceResult =
        searchServiceAPI.getAll(searchCriteria)

}