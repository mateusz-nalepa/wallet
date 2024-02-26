package com.mateuszcholyn.wallet.frontend.view.screen.backup.backupV1.export

import com.mateuszcholyn.wallet.R
import com.mateuszcholyn.wallet.frontend.domain.usecase.backup.export.ExportV1UseCase
import com.mateuszcholyn.wallet.frontend.view.screen.expenseform.MainDispatcherRule
import com.mateuszcholyn.wallet.frontend.view.screen.util.actionButton.ErrorModalState
import com.mateuszcholyn.wallet.frontend.view.screen.util.fileUtils.export.FileExportParameters
import com.mateuszcholyn.wallet.manager.randomBackupWalletV1
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ExportV1ViewModelTest {

    private lateinit var viewModel: ExportV1ViewModel

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var exportV1UseCase: ExportV1UseCase

    @Before
    fun setUp() {
        exportV1UseCase = mockk<ExportV1UseCase>(relaxed = true)

        viewModel =
            ExportV1ViewModel(
                exportV1UseCase = exportV1UseCase,
            )
    }

    @Test
    fun shouldShowErrorModalWhenUnableToGenerateBackupFile() = runTest {
        // given
        coEvery { exportV1UseCase.invoke() }.throws(RuntimeException())
        val onFileReadyActionMock = mockk<(FileExportParameters) -> Unit>()

        // when
        viewModel.exportBackupV1(
            exportLabel = "exportLabel",
            exportFileNamePrefix = "exportFileNamePrefix",
            onFileReadyAction = onFileReadyActionMock,
        )

        // then
        viewModel.exportedUiState.value.run {
            errorModalState shouldBe ErrorModalState.Visible(R.string.error_unable_to_export_data)
        }
        // and
        verify(exactly = 0) { onFileReadyActionMock.invoke(any()) }

        // and should close error modal
        viewModel.closeErrorModal()

        // and then
        viewModel.exportedUiState.value.run {
            errorModalState shouldBe ErrorModalState.NotVisible
        }
    }

    @Test
    fun shouldExportData() = runTest {
        // given
        val backupWalletV1 = randomBackupWalletV1()
        coEvery { exportV1UseCase.invoke() }.returns(backupWalletV1)
        val onFileReadyActionMock = mockk<(FileExportParameters) -> Unit>()

        // when
        viewModel.exportBackupV1(
            exportLabel = "exportLabel",
            exportFileNamePrefix = "exportFileNamePrefix",
            onFileReadyAction = onFileReadyActionMock,
        )

        // then
        viewModel.exportedUiState.value.run {
            isLoading shouldBe false
        }

        // and
        verify(exactly = 1) { onFileReadyActionMock.invoke(any()) }
    }

}
