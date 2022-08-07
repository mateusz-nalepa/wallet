package com.mateuszcholyn.wallet.newcode.app.usecase.searchservice

import com.mateuszcholyn.wallet.newcode.app.backend.searchservice.SearchCriteria
import com.mateuszcholyn.wallet.newcode.app.backend.searchservice.SearchServiceAPI
import com.mateuszcholyn.wallet.newcode.app.backend.searchservice.SearchServiceResult
import com.mateuszcholyn.wallet.newcode.app.usecase.UseCase

class SearchServiceUseCase(
    private val searchService: SearchServiceAPI,
) : UseCase {

    fun invoke(searchCriteria: SearchCriteria): SearchServiceResult =
        searchService.getAll(searchCriteria)

}