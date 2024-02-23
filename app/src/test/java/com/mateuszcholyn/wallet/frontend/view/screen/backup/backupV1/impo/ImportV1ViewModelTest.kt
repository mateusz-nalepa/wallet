package com.mateuszcholyn.wallet.frontend.view.screen.backup.backupV1.impo

import com.mateuszcholyn.wallet.R
import com.mateuszcholyn.wallet.frontend.domain.usecase.backup.impo.ImportV1UseCase
import com.mateuszcholyn.wallet.frontend.view.screen.backup.backupV1.export.BackupV1JsonCreator
import com.mateuszcholyn.wallet.frontend.view.screen.expenseform.MainDispatcherRule
import com.mateuszcholyn.wallet.frontend.view.screen.util.actionButton.ErrorModalState
import com.mateuszcholyn.wallet.frontend.view.screen.util.actionButton.SuccessModalState
import com.mateuszcholyn.wallet.manager.randomBackupWalletV1
import com.mateuszcholyn.wallet.manager.randomImportV1Summary
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

// FIXME: add tests for comparator modal dialog
class ImportV1ViewModelTest {

    private lateinit var viewModel: ImportV1ViewModel

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var importV1UseCase: ImportV1UseCase

    @Before
    fun setUp() {
        importV1UseCase = mockk<ImportV1UseCase>(relaxed = true)

        viewModel =
            ImportV1ViewModel(
                importV1UseCase = importV1UseCase,
            )
    }

    @Test
    fun `should show error modal when unable to read file content as BackupWalletV1`() = runTest {
        // given
        coEvery { importV1UseCase.invoke(any()) }.throws(RuntimeException())

        // when
        viewModel.importBackupV1 { "SOME INVALID FILE CONTENT" }

        // then
        viewModel.exportedUiState.value.run {
            errorState shouldBe ErrorModalState.Visible(R.string.error_unable_to_import_data)
            buttonIsLoading shouldBe false
            importV1SummaryProgressState shouldBe null
        }
        // and should close error modal
        viewModel.closeErrorStateModal()

        // and then
        viewModel.exportedUiState.value.run {
            errorState shouldBe ErrorModalState.NotVisible
        }
    }

    @Test
    fun `should import data and show summary`() = runTest {
        // given
        val givenRandomV1Summary = randomImportV1Summary()
        coEvery { importV1UseCase.invoke(any()) }.returns(givenRandomV1Summary)

        // when
        viewModel.importBackupV1 { BackupV1JsonCreator.createBackupWalletV1AsString(randomBackupWalletV1()) }

        // then
        viewModel.exportedUiState.value.run {
            successState shouldBe SuccessModalState.Visible(givenRandomV1Summary)
            buttonIsLoading shouldBe false
            importV1SummaryProgressState shouldBe null
        }
        // and should close import success modal modal
        viewModel.closeSuccessStateModal()

        // and then
        viewModel.exportedUiState.value.run {
            successState shouldBe SuccessModalState.NotVisible
        }
    }

}