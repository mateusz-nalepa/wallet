package com.mateuszcholyn.wallet.app.setupunittests

import com.mateuszcholyn.wallet.backend.api.categoriesquicksummary.CategoriesQuickSummaryAPI
import com.mateuszcholyn.wallet.backend.impl.domain.messagebus.MessageBus
import com.mateuszcholyn.wallet.backend.api.searchservice.SearchServiceAPI

data class MessageBusConfigParameters(
    val messageBus: MessageBus,
    val searchService: SearchServiceAPI,
    val categoriesQuickSummary: CategoriesQuickSummaryAPI,
)

class MessageBusConfigurator(
    messageBusConfigParameters: MessageBusConfigParameters,
) {

    private val messageBus = messageBusConfigParameters.messageBus
    private val searchService = messageBusConfigParameters.searchService
    private val categoriesQuickSummary = messageBusConfigParameters.categoriesQuickSummary

    fun configure() {
        configureExpenseAddedEventTopic()
        configureExpenseUpdatedEventTopic()
        configureExpenseRemovedEventTopic()

        configureCategoryAddedEventTopic()
        configureCategoryRemovedEventTopic()
    }

    private fun configureExpenseAddedEventTopic() {
        messageBus.expenseAddedEventTopic.addSubscription {
            categoriesQuickSummary.handleEventExpenseAdded(it)
        }
        messageBus.expenseAddedEventTopic.addSubscription {
            searchService.handleEventExpenseAdded(it)
        }
    }

    private fun configureExpenseUpdatedEventTopic() {
        messageBus.expenseUpdatedEventTopic.addSubscription {
            categoriesQuickSummary.handleEventExpenseUpdated(it)
        }
        messageBus.expenseUpdatedEventTopic.addSubscription {
            searchService.handleEventExpenseUpdated(it)
        }
    }

    private fun configureExpenseRemovedEventTopic() {
        messageBus.expenseRemovedEventTopic.addSubscription {
            categoriesQuickSummary.handleEventExpenseRemoved(it)
        }
        messageBus.expenseRemovedEventTopic.addSubscription {
            searchService.handleEventExpenseRemoved(it)
        }
    }

    private fun configureCategoryAddedEventTopic() {
        messageBus.categoryAddedEventTopic.addSubscription {
            categoriesQuickSummary.handleCategoryAdded(it)
        }
    }

    private fun configureCategoryRemovedEventTopic() {
        messageBus.categoryRemovedEventTopic.addSubscription {
            categoriesQuickSummary.handleCategoryRemoved(it)
        }
    }

}