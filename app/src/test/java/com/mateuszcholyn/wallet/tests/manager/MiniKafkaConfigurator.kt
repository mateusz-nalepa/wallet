package com.mateuszcholyn.wallet.tests.manager

import com.mateuszcholyn.wallet.backend.categoriesquicksummary.CategoriesQuickSummaryAPI
import com.mateuszcholyn.wallet.backend.events.MiniKafka
import com.mateuszcholyn.wallet.backend.searchservice.SearchServiceAPI

data class MiniKafkaConfigParameters(
    val miniKafka: MiniKafka,
    val searchService: SearchServiceAPI,
    val categoriesQuickSummary: CategoriesQuickSummaryAPI,
)

object MiniKafkaConfigurator {

    fun configure(miniKafkaConfigParameters: MiniKafkaConfigParameters) {

        val miniKafka = miniKafkaConfigParameters.miniKafka
        val searchService = miniKafkaConfigParameters.searchService
        val categoriesQuickSummary = miniKafkaConfigParameters.categoriesQuickSummary


        miniKafka.expenseAddedEventTopic.addSubscription {
            categoriesQuickSummary.handleEventExpenseAdded(it)
        }
        miniKafka.expenseAddedEventTopic.addSubscription {
            searchService.handleEventExpenseAdded(it)
        }

        miniKafka.categoryAddedEventTopic.addSubscription {
            categoriesQuickSummary.handleCategoryAdded(it)
        }
        miniKafka.categoryRemovedEventTopic.addSubscription {
            categoriesQuickSummary.handleCategoryRemoved(it)
        }
        miniKafka.expenseRemovedEventTopic.addSubscription {
            categoriesQuickSummary.handleEventExpenseRemoved(it)
        }
        miniKafka.expenseRemovedEventTopic.addSubscription {
            searchService.handleEventExpenseRemoved(it)
        }
    }

}