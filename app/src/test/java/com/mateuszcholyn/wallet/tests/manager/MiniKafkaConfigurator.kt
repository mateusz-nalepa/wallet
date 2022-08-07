package com.mateuszcholyn.wallet.tests.manager

import com.mateuszcholyn.wallet.backend.categoriesquicksummary.CategoriesQuickSummaryAPI
import com.mateuszcholyn.wallet.backend.events.MiniKafka
import com.mateuszcholyn.wallet.backend.searchservice.SearchServiceAPI

data class MiniKafkaConfigParameters(
    val miniKafka: MiniKafka,
    val searchService: SearchServiceAPI,
    val categoriesQuickSummary: CategoriesQuickSummaryAPI,
)

class MiniKafkaConfigurator(
    miniKafkaConfigParameters: MiniKafkaConfigParameters,
) {

    private val miniKafka = miniKafkaConfigParameters.miniKafka
    private val searchService = miniKafkaConfigParameters.searchService
    private val categoriesQuickSummary = miniKafkaConfigParameters.categoriesQuickSummary

    fun configure() {
        configureExpenseAddedEventTopic()
        configureExpenseUpdatedEventTopic()
        configureExpenseRemovedEventTopic()

        configureCategoryAddedEventTopic()
        configureCategoryRemovedEventTopic()
    }

    private fun configureExpenseAddedEventTopic() {
        miniKafka.expenseAddedEventTopic.addSubscription {
            categoriesQuickSummary.handleEventExpenseAdded(it)
        }
        miniKafka.expenseAddedEventTopic.addSubscription {
            searchService.handleEventExpenseAdded(it)
        }
    }

    private fun configureExpenseUpdatedEventTopic() {
        miniKafka.expenseUpdatedEventTopic.addSubscription {
            categoriesQuickSummary.handleEventExpenseUpdated(it)
        }
        miniKafka.expenseUpdatedEventTopic.addSubscription {
            searchService.handleEventExpenseUpdated(it)
        }
    }

    private fun configureExpenseRemovedEventTopic() {
        miniKafka.expenseRemovedEventTopic.addSubscription {
            categoriesQuickSummary.handleEventExpenseRemoved(it)
        }
        miniKafka.expenseRemovedEventTopic.addSubscription {
            searchService.handleEventExpenseRemoved(it)
        }
    }

    private fun configureCategoryAddedEventTopic() {
        miniKafka.categoryAddedEventTopic.addSubscription {
            categoriesQuickSummary.handleCategoryAdded(it)
        }
    }

    private fun configureCategoryRemovedEventTopic() {
        miniKafka.categoryRemovedEventTopic.addSubscription {
            categoriesQuickSummary.handleCategoryRemoved(it)
        }
    }

}