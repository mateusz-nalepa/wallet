package com.mateuszcholyn.wallet.frontend.domain.demomode

import com.mateuszcholyn.wallet.frontend.domain.usecase.ExpenseAppUseCases
import com.mateuszcholyn.wallet.manager.ExpenseAppInitializer
import com.mateuszcholyn.wallet.manager.ExpenseAppManagerScope
import com.mateuszcholyn.wallet.manager.category
import com.mateuszcholyn.wallet.manager.expense
import com.mateuszcholyn.wallet.util.localDateTimeUtils.fromUserLocalTimeZoneToUTCInstant
import com.mateuszcholyn.wallet.util.localDateTimeUtils.minusDays
import java.math.BigDecimal
import java.time.LocalDateTime

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
        val instantNow = LocalDateTime.now().fromUserLocalTimeZoneToUTCInstant()

        initDemoApp {
            category {
                categoryName = "test 1"
                expense {
                    amount = BigDecimal("8.45")
                    paidAt = instantNow
                    description = "test"
                }
                expense {
                    amount = BigDecimal("5.96")
                    paidAt = instantNow.minusDays(1)
                    description = "test"
                }
                expense {
                    amount = BigDecimal("12.95")
                    paidAt = instantNow.minusDays(1)
                    description = "test"
                }
                expense {
                    amount = BigDecimal("15.95")
                    paidAt = instantNow.minusDays(2)
                    description = "test"
                }
                expense {
                    amount = BigDecimal("17.95")
                    paidAt = instantNow.minusDays(3)
                    description = "test"
                }
                expense {
                    amount = BigDecimal("20.95")
                    paidAt = instantNow.minusDays(30)
                    description = "test"
                }
                expense {
                    amount = BigDecimal("17.95")
                    paidAt = instantNow.minusDays(60)
                    description = "test"
                }
                expense {
                    amount = BigDecimal("55.95")
                    paidAt = instantNow.minusDays(90)
                    description = "test"
                }
                expense {
                    amount = BigDecimal("20.95")
                    paidAt = instantNow.minusDays(400)
                    description = "test"
                }
            }
            category {
                categoryName = "test 2"
                expense {
                    amount = BigDecimal("128.94")
                    description = "test test"
                    paidAt = instantNow
                }
                expense {
                    amount = BigDecimal("2.94")
                    description = "test test"
                    paidAt = instantNow.minusDays(3)
                }
                expense {
                    amount = BigDecimal("22.94")
                    description = "test test"
                    paidAt = instantNow.minusDays(30)
                }
                expense {
                    amount = BigDecimal("24.94")
                    description = "test test"
                    paidAt = instantNow.minusDays(60)
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
