package com.mateuszcholyn.wallet.frontend.view.skeleton.topBar

import com.mateuszcholyn.wallet.frontend.domain.demomode.DemoAppSwitcher
import com.mateuszcholyn.wallet.frontend.view.screen.expenseform.MainDispatcherRule
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.random.Random

class TopBarViewModelTest {


    private lateinit var viewModel: TopBarViewModel

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var demoAppSwitcher: DemoAppSwitcher

    @Before
    fun setUp() {
        demoAppSwitcher = mockk<DemoAppSwitcher>(relaxed = true)

        viewModel =
            TopBarViewModel(
                demoAppSwitcher = demoAppSwitcher,
            )
    }

    @Test
    fun `default top bar ui state should show demo mode disabled`() = runTest {
        // expect
        viewModel.exposedTopBarUiState.value.run {
            isDemoModeEnabled shouldBe false
        }
    }

    @Test
    fun `should show demo mode badge based on demo app switcher response`() = runTest {
        // given
        val givenIsDemoModeEnabled = Random.nextBoolean()
        every { demoAppSwitcher.isDemoModeEnabled() }.returns(givenIsDemoModeEnabled)

        // when
        viewModel.loadTopBarState()

        // expect
        viewModel.exposedTopBarUiState.value.run {
            isDemoModeEnabled shouldBe givenIsDemoModeEnabled
        }
    }

}