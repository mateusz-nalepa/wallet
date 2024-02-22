package com.mateuszcholyn.wallet.frontend.domain.demomode

import com.mateuszcholyn.wallet.frontend.domain.usecase.ExpenseAppUseCases
import com.mateuszcholyn.wallet.manager.ExpenseAppInitializer
import com.mateuszcholyn.wallet.manager.ExpenseAppManagerScope
import com.mateuszcholyn.wallet.manager.category
import com.mateuszcholyn.wallet.manager.expense
import com.mateuszcholyn.wallet.util.localDateTimeUtils.minusDays
import java.math.BigDecimal
import java.time.Instant

class DemoModeInitializer(
    private val demoAppSwitcher: DemoAppSwitcher,
    private val expenseAppUseCases: ExpenseAppUseCases,
) {
    fun init() {
        if (!demoAppSwitcher.isDemoModeEnabled()) {
            return
        }
        initBecauseDemoModeIsEnabled()
    }

    private fun initBecauseDemoModeIsEnabled() {
        initDemoApp {
            category {
                categoryName = "Zakupy"
                expense {
                    amount = BigDecimal("8.45")
                }
                expense {
                    amount = BigDecimal("5.96")
                    description = "Bułki"
                }
                expense {
                    amount = BigDecimal("12.95")
                    paidAt = Instant.now().minusDays(1)
                }
                expense {
                    amount = BigDecimal("15.95")
                    paidAt = Instant.now().minusDays(2)
                }
                expense {
                    amount = BigDecimal("17.95")
                    paidAt = Instant.now().minusDays(3)
                }
                expense {
                    amount = BigDecimal("17.95")
                    paidAt = Instant.now().minusDays(60)
                }
                expense {
                    amount = BigDecimal("55.95")
                    paidAt = Instant.now().minusDays(90)
                }
                expense {
                    amount = BigDecimal("20.95")
                    paidAt = Instant.now().minusDays(400)
                }
            }
            category {
                categoryName = "Paliwo"
                expense {
                    amount = BigDecimal("128.94")
                    description = "gaz\n2.96"
                }
            }
        }

    }

    private fun initDemoApp(
        scope: ExpenseAppManagerScope.() -> Unit,
    ) {
        val expenseAppManagerScope = ExpenseAppManagerScope().apply(scope)

        ExpenseAppInitializer(
            expenseAppManagerScope = expenseAppManagerScope,
            expenseAppUseCases = expenseAppUseCases,
        ).init()
    }

}
