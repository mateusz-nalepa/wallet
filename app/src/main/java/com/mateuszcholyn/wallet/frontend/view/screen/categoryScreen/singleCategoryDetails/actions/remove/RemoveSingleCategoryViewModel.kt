package com.mateuszcholyn.wallet.frontend.view.screen.categoryScreen.singleCategoryDetails.actions.remove

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mateuszcholyn.wallet.backend.api.core.category.CategoryId
import com.mateuszcholyn.wallet.frontend.domain.usecase.core.category.RemoveCategoryUseCase
import com.mateuszcholyn.wallet.frontend.view.screen.util.actionButton.ButtonActions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RemoveSingleCategoryViewModel @Inject constructor(
    private val removeCategoryUseCase: RemoveCategoryUseCase,
) : ViewModel() {

    fun removeCategoryById(
        categoryId: CategoryId,
        buttonActions: ButtonActions,
    ) {
        viewModelScope.launch { // DONE
            try {
                removeCategoryUseCase.invoke(categoryId)
                buttonActions.onSuccessAction.invoke()
            } catch (t: Throwable) {
                buttonActions.onErrorAction.invoke("Nie udalo sie usunac kategorii")
            }
        }
    }
}
