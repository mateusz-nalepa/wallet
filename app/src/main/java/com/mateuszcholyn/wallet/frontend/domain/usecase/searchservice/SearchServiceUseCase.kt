package com.mateuszcholyn.wallet.frontend.domain.usecase.searchservice

import com.mateuszcholyn.wallet.backend.api.searchservice.SearchCriteria
import com.mateuszcholyn.wallet.backend.api.searchservice.SearchServiceAPI
import com.mateuszcholyn.wallet.backend.api.searchservice.SearchServiceResult
import com.mateuszcholyn.wallet.frontend.domain.usecase.UseCase

class SearchServiceUseCase(
    private val searchService: SearchServiceAPI,
) : UseCase {

    suspend fun invoke(searchCriteria: SearchCriteria): SearchServiceResult =
        searchService.getAll(searchCriteria)

}